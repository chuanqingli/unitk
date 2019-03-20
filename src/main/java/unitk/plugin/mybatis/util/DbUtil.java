package unitk.plugin.mybatis.util;

import unitk.plugin.mybatis.mapper.MyBatisMapper;
import java.util.*;
import org.apache.ibatis.session.SqlSession;

import unitk.plugin.proxool.ProxoolBean;
import unitk.plugin.proxool.ProxoolUtil;

import unitk.util.BeanUtil;
import unitk.util.DataUtil;
import unitk.util.DataSourceHolder;

// import unitk.db.*;
// import unitk.vo.*;

public final class DbUtil implements unitk.util.DbUtil{
    private static final Map<String,ProxoolBean> __dbappmap = getDbAppMap00();
    //map的String,ProxoolBean分别为appid,首个master库的ProxoolBean
    private static Map<String,ProxoolBean> getDbAppMap00(){
        Map<String,String> app = (Map<String,String>)BeanUtil.getInstance().getBean("dbmap");
        if(app==null||app.size()<=0)return null;

        Map<String,ProxoolBean> proxoolmap = ProxoolUtil.getInstance().getProxoolMap();
        if(proxoolmap==null||proxoolmap.size()<=0)return null;

        Map<String,ProxoolBean> resp = new java.util.concurrent.ConcurrentHashMap<String,ProxoolBean>();
        for (Map.Entry<String,String> entry: app.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            val = val.replaceAll(" ","");
            /*
                    <!-- 映射关系语法: ';'号分从库节点关系标记, ':'号节点主从库(master-slave)关系标记, ','号分数据库主从(master-slave)组标记 -->
                    <!-- 例子: m0:s0;s1;s2,m1:s0;s1,m2:s0;s1;s2 m0 s0 ..... 是proxool连接池别名 -->
                    <!--1主3从,1主2从,1主3从-->
             */

            String master = val.split("[,:;]")[0];
            // logger.info("key,value:" + JsonUtil.toJson(entry) + ":" + JsonUtil.toJson(new Object[]{key,val,master}));
            ProxoolBean ppp = proxoolmap.get(master);
            if(ppp==null)continue;
            ppp.morealias = val;
            ppp.masteralias = master;

            String dbUrl = ppp.driverUrl.trim().toLowerCase();
            ppp.dbtypeName = "mysql";
            if(dbUrl.startsWith("jdbc:jtds:sqlserver://"))ppp.dbtypeName = "sqlserver";
            resp.put(key,ppp);
        }
        return resp;
    }

    protected final MyBatisMapper getMapper(SqlSession session){
        return session.getMapper(MyBatisMapper.class);
    }

    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    //创建能执行映射文件中sql的sqlSession
    private SqlSession getSession(String key){
        if(key==null||key.length()<=0)return null;

        //resin ok
        DataSourceHolder.setDataSourceType(key);
        return BeanUtil.getInstance().getBean("sqlSession",SqlSession.class);

        //ice ok
        // ApplicationContext appContext = new ClassPathXmlApplicationContext("/config/spring.xml");
        // return (SqlSession)appContext.getBean(key);
    }

    protected final SqlSession getSqlSession(Map<String,Object> map){
        String dbid = toData(map,"_dbid","");
        if(dbid==null||dbid.length()<=0)return null;

        String dbtype = getDbTypeName(dbid);
        if(dbtype==null||dbtype.length()<=0)return null;
        map.put("_dbtype",dbtype);

        StackTraceElement[] strcss = Thread.currentThread().getStackTrace();
        StackTraceElement strc = strcss[2];

        String method = strc.getMethodName();
        if(method.startsWith("select")){
            String dbroute = toData(map,"_dbroute","");
            if("rand".equals(dbroute))return getRandSession(dbid);
            if("slave".equals(dbroute))return getSlaveSession(dbid);
        }
        return getMasterSession(dbid);
    }

    private String getDbTypeName(String dbId){
        // logger.info("dddd=>" + JsonUtil.toJson(__dbappmap));
        ProxoolBean ppp = __dbappmap.get(dbId);
        if(ppp==null)return null;
        return ppp.dbtypeName;
    }

    public final SqlSession getMasterSession(String key){
        return getMaster(key);
    }

    public final SqlSession getSlaveSession(String key){
        return getSlave(key);
    }

    public final SqlSession getRandSession(String key){
        return getRand(key);
    }

    //注意：这里没有用到更多的库，所以不做太深入分析了
    public String getMasterId(String dbId){
        ProxoolBean ppp = getProxoolBean(dbId);
        if(ppp==null)return null;
        String[] sss = ppp.morealias.split(":");
        return sss[0];
     }

    //注意：这里没有用到更多的库，所以不做太深入分析了
    public String getSlaveId(String dbId){
        ProxoolBean ppp = getProxoolBean(dbId);
        if(ppp==null)return null;
        String[] sss = ppp.morealias.split(":");
        return sss[1].split(";")[0];
    }

    //取值范围为[min,max],前后次序颠倒也不影响
    private long getRandomLong(long min,long max){
        return Math.round(Math.random()*(max-min)+min);
    }

    public String getRandId(String dbId){
        ProxoolBean ppp = getProxoolBean(dbId);
        if(ppp==null)return null;

        String[] sss = ppp.morealias.split("[,:;]");
        List<String> sslist = new ArrayList<String>();
        for(String ss : sss){
            if(ss.length()<=0||sslist.contains(ss))continue;
            sslist.add(ss);
        }

        int nnn = (int)getRandomLong(0,sslist.size()-1);
        return sslist.get(nnn);
    }

    private ProxoolBean getProxoolBean(String key){
        if(key==null||key.length()<=0)return null;
        return __dbappmap.get(key);
    }


    public SqlSession getMaster(String key){
        String dbid = getMasterId(key);
        return getSession(dbid);
    }

    public SqlSession getSlave(String key){
        String dbid = getSlaveId(key);
        return getSession(dbid);
    }

    public SqlSession getRand(String key){
        String dbid = getRandId(key);
        return getSession(dbid);
    }

    public final List<Map<String,Object>> selectList(Map<String,Object> map){
        SqlSession session = getSqlSession(map);
        return getMapper(session).selectList(map);
    }

}

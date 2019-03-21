package unitk.plugin.mybatis.util;

import unitk.plugin.mybatis.mapper.MyBatisMapper;
import java.util.*;
import org.apache.ibatis.session.SqlSession;

import unitk.plugin.proxool.DbmapUtil;

import unitk.util.BeanUtil;
import unitk.util.DataUtil;
import unitk.util.PageUtil;
import unitk.util.ListPageBean;
import unitk.plugin.DataSourceSwitch;

public final class DbUtil implements unitk.util.DbUtil{

    private final MyBatisMapper getMapper(SqlSession session){
        return session.getMapper(MyBatisMapper.class);
    }

    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    private void chkstrkey(String key,String name){
        if(key==null||key.length()<=0)throw new RuntimeException("SqlSession异常:" + name + "未赋值");
    }

    //创建能执行映射文件中sql的sqlSession
    private SqlSession getSession(String key){
        chkstrkey(key,"key");
        //resin ok
        DataSourceSwitch.setDataSourceType(key);
        return BeanUtil.getInstance().getBean("sqlSession",SqlSession.class);

        //ice ok
        // ApplicationContext appContext = new ClassPathXmlApplicationContext("/config/spring.xml");
        // return (SqlSession)appContext.getBean(key);
    }

    private final SqlSession getSqlSession(Map<String,Object> map){
        String dbid = toData(map,"_dbid","");
        chkstrkey(dbid,"dbid");

        String dbtype = DbmapUtil.getInstance().getDbTypeName(dbid);
        chkstrkey(dbtype,"dbtype");
        map.put("_dbtype",dbtype);

        StackTraceElement[] strcss = Thread.currentThread().getStackTrace();
        StackTraceElement strc = strcss[2];

        String method = strc.getMethodName();
        String dbroute = "";
        if(method.startsWith("select")){
            dbroute = toData(map,"_dbroute","");
        }
        String routeid = DbmapUtil.getInstance().getRouteId(dbroute,dbid);
        return getSession(routeid);
    }

    public final List<Map<String,Object>> selectList(Map<String,Object> map){
        SqlSession session = getSqlSession(map);
        return getMapper(session).selectList(map);
    }




    // public final List<Map<String,Object>> call(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).call(map);
    // }

    // public final int insert(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).insert(map);
    // }

    // public final int insertAndGetKey(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).insertAndGetKey(map);
    // }


    // public final int update(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).update(map);
    // }

    // public final int delete(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).delete(map);
    // }

    // public final Map<String,Object> selectOne(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).selectOne(map);
    // }

    // public final int selectCount(Map<String,Object> map){
    //     SqlSession session = getSqlSession(map);
    //     return getMapper(session).selectCount(map);
    // }

    // //在selectList参数的基础上，增加_datacount,_pagenumber
    // public final ListPageBean<Map<String,Object>> selectPage(Map<String,Object> map){
    //     int datacount = toData(map,"_datacount",0);
    //     int pagenumber = toData(map,"_pagenumber",0);
    //     int pagesize = toData(map,"_pagesize",0);
    //     if(pagenumber<1||pagesize<=0)return null;

    //     //datacount<0时，触发selectCount查询
    //     if(datacount<0){
    //         Map<String,Object> map0 = new HashMap<String,Object>(map);
    //         // logger.debug("tttttt==>" + map0);
    //         datacount = selectCount(map0);
    //     }

    //     int pagecount = PageUtil.getInstance().getPageCount(datacount,pagesize);
    //     if(pagecount<=0)return null;

    //     if(pagenumber>pagecount)return null;
    //     int lastsize = PageUtil.getInstance().getLastSize(datacount,pagesize);

    //     int skipsize = (pagenumber-1)*pagesize;
    //     Map<String,Object> map1 = new HashMap<String,Object>(map);
    //     map1.put("_skipsize",skipsize);

    //     List<Map<String,Object>> pagelist = selectList(map1);

    //     ListPageBean<Map<String,Object>> pp = new ListPageBean<Map<String,Object>>();
    //     pp.setDataCount(datacount);
    //     pp.setPageCount(pagecount);
    //     pp.setLastSize(lastsize);
    //     pp.setPageNumber(pagenumber);
    //     pp.setPageSize(pagesize);
    //     pp.setDataList(pagelist);
    //     return pp;
    // }

    // public final List<Map<String,Object>> queryList(Map<String,Object> map){
    //     map.put("_dbroute","slave");
    //     return selectList(map);
    // }
    // public final Map<String,Object> queryOne(Map<String,Object> map){
    //     map.put("_dbroute","slave");
    //     return selectOne(map);
    // }
    // public final int queryCount(Map<String,Object> map){
    //     map.put("_dbroute","slave");
    //     return selectCount(map);
    // }
    // protected final ListPageBean<Map<String,Object>> queryPage(Map<String,Object> map){
    //     map.put("_dbroute","slave");
    //     return selectPage(map);
    // }

    // public final Map<String,Object> queryCacheOne(Map<String,Object> map,CacheBean<Map<String,Object>> cache){
    //     map.put("_dbroute","slave");
    //     return selectCacheOne(map,cache);
    // }
    // public final List<Map<String,Object>> queryCacheList(Map<String,Object> map,CacheBean<List<Map<String,Object>>> cache){
    //     map.put("_dbroute","slave");
    //     return selectCacheList(map,cache);
    // }
    // public final ListPageBean<Map<String,Object>> queryCachePage(Map<String,Object> map,CacheBean<ListPageBean<Map<String,Object>>> cache){
    //     map.put("_dbroute","slave");
    //     return selectCachePage(map,cache);
    // }

    // protected final List<Map<String,Object>> execProc(String dbid,String sql,Set<String> addedset,Map<String,Object> inmap){
    //     Map<String,Object> reqmap = new HashMap<String,Object>(inmap);
    //     reqmap.putAll(SetMapUtil.arr2Map(new String[]{"_dbid","_sql"},new Object[]{dbid,sql}));
    //     List<Map<String,Object>> resplist = call(reqmap);
    //     if(addedset==null||addedset.size()<=0)return resplist;
    //     Map<String,Object> tmpmap = SetMapUtil.mapOpSet("*",reqmap,addedset);
    //     inmap.putAll(tmpmap);
    //     return resplist;
    // }

}

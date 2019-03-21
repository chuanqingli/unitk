package unitk.util;
import java.util.*;
import unitk.vo.SqlBean;
public final class PageSqlUtil{
    private PageSqlUtil(){}
    private final static PageSqlUtil __instance = new PageSqlUtil();
    public static PageSqlUtil getInstance(){
        return __instance;
    }

    private void chkstrkey(String key,String name){
        if(key==null||key.length()<=0)throw new RuntimeException(name + "未赋值");
    }

    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    public String getSql(Map<String,Object> map){
        String dbid = toData(map,"_dbid","");
        chkstrkey(dbid,"dbid");
        String dbtype = toData(map,"_dbtype","");
        chkstrkey(dbtype,"dbtype");
        String sql = toData(map,"_sql","");
        if(sql!=null&&sql.length()>0)return sql;

        SqlBean vo = new SqlBean();
        VoMapUtil vomapUtil = new VoMapUtil();
        vomapUtil.copyMapToVo(map,vo);

        return SqlUtil.getInstance(dbtype).getPageSql(vo);
        // return PageSqlHandler.getInstance(dbtype).getSql(unisql,order,skipsize,pagesize);
    }
}

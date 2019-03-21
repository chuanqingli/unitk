package unitk.util;
import java.util.*;
public final class PageSqlUtil{

    private void chkstrkey(String key,String name){
        if(key==null||key.length()<=0)throw new RuntimeException(name + "未赋值");
    }

    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    public String getSql(Map<String,Object> map){
        String dbid = toData(map,"_dbid","");
        chkstrkey(dbid,"dbid");

        String unisql = toData(map,"_unisql","");
        chkstrkey(unisql,"unisql");
        String order = toData(map,"_order","");
        if(order==null)order = "";
        int skipsize = toData(map,"_skipsize",0);
        int pagesize = toData(map,"_pagesize",0);

        String dbtype = toData(map,"_dbtype","");
        chkstrkey(dbtype,"dbtype");

        return SqlUtil.getInstance(dbtype).getPageSql(unisql,order,skipsize,pagesize);
        // return PageSqlHandler.getInstance(dbtype).getSql(unisql,order,skipsize,pagesize);
    }
}

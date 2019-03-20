package unitk.util;
import java.util.*;
interface PageSqlHandler{
    String getSql(String unisql,String order,int skipsize,int pagesize);

    static Map<String,PageSqlHandler> __map = getMap();

    static Map<String,PageSqlHandler> getMap(){
        Map<String,PageSqlHandler> map = new java.util.concurrent.ConcurrentHashMap<String,PageSqlHandler>();
        Set<Class> set = PackageUtil.getInstance().getImplSet(PageSqlHandler.class);
        for(Class key : set){
            String ss = key.getSimpleName().split("_")[1];
            map.put(ss,getHandler(key));
        }
        return map;
    }

    static PageSqlHandler getHandler(Class key){
        try{
            return (PageSqlHandler)key.newInstance();
        }catch(Exception err){
            throw new RuntimeException(err);
        }
    }

    static PageSqlHandler getInstance(String key){
        return __map.get(key);
    }
}

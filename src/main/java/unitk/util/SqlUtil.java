package unitk.util;
import java.util.*;
public interface SqlUtil{

    static SqlUtil getInstance(String dbtype){
        return ttt.__map.get(dbtype);
    }

    String getPageSql(String unisql,String order,int skipsize,int pagesize);

    class ttt{

        private static Map<String,SqlUtil> __map = getMap();

        private static Map<String,SqlUtil> getMap(){
            Map<String,SqlUtil> map = new java.util.concurrent.ConcurrentHashMap<String,SqlUtil>();
            Set<Class> set = PackageUtil.getInstance().getImplSet(SqlUtil.class);
            for(Class key : set){
                String ss = key.getSimpleName().split("_")[1];
                map.put(ss,getBean(key));
            }
            return map;
        }

        private static SqlUtil getBean(Class key){
            try{
                return (SqlUtil)key.newInstance();
            }catch(Exception err){
                throw new RuntimeException(err);
            }
        }
    }



}

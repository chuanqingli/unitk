package unitk.util;
import java.util.*;
import unitk.vo.SqlBean;
public interface SqlUtil{

    static SqlUtil getInstance(String dbtype){
        return in.__map.get(dbtype);
    }

    String getPageSql(SqlBean b);


    abstract class in implements SqlUtil{

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

        protected String wrap(Object... o){
            return StringUtil.getInstance().wrap(new StringBuffer(1024),"","",o).toString();
        }

        public final String getPageSql(SqlBean b){
            if(b._sql!=null&&b._sql.length()>0)return b._sql;

            if(b._unisql!=null&&b._unisql.length()>0){
                return getPageSql_unisql(b._unisql,b._order,b._skipsize,b._pagesize);
            }

            return getPageSql_table(b);
        }

        abstract String getPageSql_unisql(String unisql,String order,int skipsize,int pagesize);
        abstract String getPageSql_table(SqlBean b);

    }
}

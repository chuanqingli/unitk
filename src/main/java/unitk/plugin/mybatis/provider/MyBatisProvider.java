package unitk.plugin.mybatis.provider;
import java.util.*;
import unitk.util.*;

public final class MyBatisProvider{
    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    public final String __selectList(Map<String,Object> map){
        String sql = toData(map,"_sql","");
        if(sql!=null&&sql.length()>0)return sql;

        String unisql = toData(map,"_unisql","");
        // logger.debug("unisql==>" + unisql);
        if(unisql!=null&&unisql.length()>0){
            return PageSqlUtil.getInstance().getSql(map);
        }
        return null;
    }
}

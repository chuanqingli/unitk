package unitk.plugin.mybatis.provider;
import java.util.*;
import unitk.util.*;

public final class MyBatisProvider{
    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }


    public final String __selectList(Map<String,Object> map){
        String sql = toData(map,"_sql","");
        return sql;
    }
}

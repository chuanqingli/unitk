package unitk.plugin.mybatis.provider;
import java.util.*;
import unitk.util.*;

public final class MyBatisProvider{
    private <T> T toData(Map<String,Object> map,String name,T cc){
        return DataUtil.getInstance().toData(map.get(name),cc);
    }

    public final String __call(Map<String,Object> map){
        if(map==null||map.size()<=0)return null;
        String sql = toData(map,"_sql","");
        if(sql!=null&&sql.length()>0)return sql;
        return null;
    }


    public final String __update(Map<String,Object> map){
        return __call(map);
    }

    public final String __delete(Map<String,Object> map){
        return __call(map);
    }

    public final String __insert(Map<String,Object> map){
        return __call(map);
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

    public final String __selectOne(Map<String,Object> map){
        if(map==null||map.size()<=0)return null;
        map.put("_pagesize",1);
        return __selectList(map);
    }

    private String wrap(Object... o){
        return StringUtil.getInstance().wrap(new StringBuilder(1024),"","",o).toString();
    }

    public final String __selectCount(Map<String,Object> map){
        if(map==null||map.size()<=0)return null;

        String unisql = toData(map,"_unisql","");
        if(unisql!=null&&unisql.length()>0){
            return wrap("select count(*) as total from (",unisql,") _t0");
        }

        map.put("_fields","count(*) as total");
        map.put("_skipsize",0);
        map.put("_pagesize",0);
        map.put("_order","");
        return __selectList(map);
    }
}

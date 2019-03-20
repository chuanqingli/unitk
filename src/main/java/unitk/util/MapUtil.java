package unitk.util;
import java.util.*;

public final class MapUtil{

    private MapUtil(){}
    private final static MapUtil __instance = new MapUtil();
    public static MapUtil getInstance(){
        return __instance;
    }

    private <T> T toData(Object oo,T cc){
        return toData(oo,cc,false);
    }
    //空或异常时，返回默认值;bthrow异常时是否抛出
    private <T> T toData(Object oo,T cc,boolean isthrow){
        return DataUtil.getInstance().toData(oo,cc,isthrow);
    }

    public <A extends Map<K,V>,K,V> A create(A resp,K[] args0,V[] args1){
        if(resp==null||args0==null||args1==null)return resp;
        for(int i=0;i<args0.length&&i<args1.length;i++){
            resp.put(args0[i],args1[i]);
        }
        return resp;
    }

    // @SuppressWarnings(value="unchecked")
    // public <A extends Map> A create(A resp,A args){
    //     if(resp==null||args==null)return resp;
    //     resp.putAll(args);
    //     return resp;
    // }

    public <A extends Map<K,V>,K,V> A create(A resp,K kk,V vv,Object[] args0,Object[] args1){
        if(resp==null||args0==null||args1==null)return resp;
        for(int i=0;i<args0.length&&i<args1.length;i++){
            K kk0 = toData(args0[i],kk);
            V vv0 = toData(args1[i],vv);
            resp.put(kk0,vv0);
        }
        return resp;
    }

    @SuppressWarnings(value="unchecked")
    public <A extends Map<K,V>,K,V> A create(A resp,K kk,V vv,Map args){
        if(resp==null||args==null)return resp;
        Set<Map.Entry> ttt = args.entrySet();
        for(Map.Entry entry : ttt){
            K kk0 = toData(entry.getKey(),kk);
            V vv0 = toData(entry.getValue(),vv);
            resp.put(kk0,vv0);
        }
        return resp;
    }
}

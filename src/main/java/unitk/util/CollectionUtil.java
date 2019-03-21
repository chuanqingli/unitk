package unitk.util;

import java.util.*;

/**集合处理
javac -Xlint:unchecked DataUtil.java DateUtil.java CollectionUtil.java
javac -Xlint:unchecked *.java
rm *.class

 */
public final class CollectionUtil{
    private CollectionUtil(){}
    private final static CollectionUtil __instance = new CollectionUtil();
    public static CollectionUtil getInstance(){
        return __instance;
    }
    private <T> T toData(Object oo,T cc){
        return toData(oo,cc,false);
    }
    //空或异常时，返回默认值;bthrow异常时是否抛出
    private <T> T toData(Object oo,T cc,boolean isthrow){
        return DataUtil.getInstance().toData(oo,cc,isthrow);
    }

    @SafeVarargs
    public final <A extends Collection<T>,T> A create(A resp,T... args){
        if(resp==null||args==null)return resp;
        resp.addAll(Arrays.asList(args));
        return resp;
    }

    public final <A extends Collection<T>,T> A create(A resp,T cc,Object... args){
        if(resp==null||args==null)return resp;
        Collection ccc = Arrays.asList(args);
        return create(resp,cc,ccc);
    }

    public final <A extends Collection<T>,T> A create(A resp,T cc,Collection args){
        if(resp==null||args==null)return resp;
        for(Object oo : args){
            resp.add(toData(oo,cc));
        }
        return resp;
    }
}

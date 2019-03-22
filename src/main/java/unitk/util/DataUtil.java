package unitk.util;

import java.util.*;
import java.math.*;
import java.lang.reflect.*;
import java.sql.Timestamp;
public final class DataUtil{

    private DataUtil(){}
    private final static DataUtil __instance = new DataUtil();
    public static DataUtil getInstance(){
        return __instance;
    }

    public <T> T toData(Object oo,T cc){
        return toData(oo,cc,false);
    }

    @SuppressWarnings(value="unchecked")
    private <K,T> T toData(Class cccls,K val,Class valcls,T cc,boolean isthrow){
        try{
            Constructor c1=cccls.getDeclaredConstructor(new Class[]{valcls});
            return (T)c1.newInstance(new Object[]{val});
        }catch(Exception err){
            if(isthrow)throw new RuntimeException("数据转换时发生异常",err);
            return cc;
        }
    }

    //空或异常时，返回默认值;bthrow异常时是否抛出
    @SuppressWarnings(value="unchecked")
    public <T> T toData(Object oo,T cc,boolean isthrow){
        if(oo==null||cc==null)return cc;

        Class cccls = cc.getClass();

        //oo能否转为cc对应的类,oo是不是cc对应类的一个实例
        if(cccls.isInstance(oo)){
            return (T)oo;
        }

        if(cc instanceof CharSequence){
            return toData(cccls,oo.toString(),String.class,cc,isthrow);
        }

        if(cc instanceof Number){
            String ss = oo.toString();

            if(cc instanceof Number){
                int nindex = ss.indexOf(".");
                if(nindex>0){
                    if(((cc instanceof Integer)==true)||((cc instanceof Long)==true)||((cc instanceof BigInteger)==true)){
                        ss = ss.substring(0,nindex);
                    }
                }
            }
            return toData(cccls,ss,String.class,cc,isthrow);
        }

        if(cc instanceof Date){
            String ss = oo.toString().trim();
            if(ss.matches(".*[^0-9]+.*")){//含有非数字的情况
                try{
                    return (T)DateUtil.getInstance().toDate(ss,true);
                }catch(Exception err){
                    if(isthrow)throw new RuntimeException("数据(" + ss + ")转换时发生异常",err);
                    return cc;
                }
            }else{
                long ltime = toData(oo,0l,isthrow);
                return toData(cccls,ltime,long.class,cc,isthrow);
            }
        }

        return (T)oo;
    }

    private String wrapsql(String s){
        return StringUtil.getInstance().wrap(new StringBuilder(1024),"'","'",s).toString();
    }

    public String sqlValue(Object o){
        if(o==null)return "''";
        if(o instanceof CharSequence){
            return wrapsql(o.toString().replace("'","''"));
        }
        if(o instanceof Number){
            return o.toString();
        }

        if(o instanceof Date){
            Date dd = (Date)o;
            String ss = new Timestamp(dd.getTime()).toString();
            return wrapsql(ss);
        }
        return wrapsql(o.toString().replace("'","''"));
    }
}

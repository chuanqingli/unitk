package unitk.util;

import java.io.*;
import java.util.*;

public final class StringUtil{

    private StringUtil(){}
    private final static StringUtil __instance = new StringUtil();
    public static StringUtil getInstance(){
        return __instance;
    }

    public <A extends Appendable> A wrap(A buf,Object o1,Object o2,Object... objs){
        return wrap(buf,o1,o2,Arrays.asList(objs));
    }
    public <A extends Appendable> A wrap(A buf,Object o1,Object o2,Collection objs){
        try{
            return create00(buf,o1,o2,objs);
        }catch(IOException err){
            throw new RuntimeException("数据转化时异常",err);
        }
    }

    //按指定长度分割字符串
    public List<String> split(String sss,List<Integer> len){
        List<String> resp = new ArrayList<String>();
        if(sss==null||sss.length()<=0||len==null||len.size()<=0)return resp;
        StringBuilder stime = new StringBuilder(sss);
        for(int lll : len){
            if(stime.length()<lll)break;
            resp.add(stime.substring(0,lll));
            stime.delete(0,lll);
            // stime = stime.substring(lll);
        }
        if(resp.size()>=len.size())return resp;
        if(stime.length()>0)resp.add(stime.toString());
        return resp;
    }

    private String toString(Object obj){
        if(obj==null)return "";
        return String.valueOf(obj);
    }

    private <A extends Appendable> A create00(A buf,Object o1,Object o2,Collection objs)throws IOException{
        if(objs==null||objs.size()<=0)return buf;
        String s1 = toString(o1);
        String s2 = toString(o2);
        for(Object obj : objs){
            String ss = toString(obj);
            buf.append(s1);
            buf.append(ss);
            buf.append(s2);
        }
        return buf;
    }
}

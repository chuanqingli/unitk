package unitk.util;

import java.util.*;
import java.lang.reflect.*;

/*
javac -encoding utf-8 VoMapUtil.java
java VoMapUtil
*/
public class VoMapUtil{
    private Map<Class,Map<String,Field>> __vosFieldMap = new HashMap<Class,Map<String,Field>>();//字段集字符串缓存

    private Map<String,String> __shareKeyMap = null;//字段集字符串缓存
    private Method __rsMethod = null;

    public VoMapUtil(){}

    //变量字段列表
    private Map getFieldMap(Class cls){
        HashMap map = new HashMap<String,Field>();
        for(Class<?> clazz = cls; clazz != Object.class; clazz = clazz.getSuperclass()) {
             try {
                Field[] fs = clazz.getDeclaredFields();
                for(int i=0;i<fs.length;i++){
                    String fsName = fs[i].getName();
                    fs[i].setAccessible(true);
                    map.put(fsName,fs[i]);
                }
             } catch (Exception e) {}
        }
        return map;
    }


    public VoMapUtil initShareKey(Map key){
        if(key!=null&&key.size()<=0)key = null;
        __shareKeyMap = key;
        return this;
    }

    //共用关键字，前两个符号为分隔符，第一个分隔字段节点，第二个用于表示该节点内，从源到目标的转换关系
    //该方法对所有字段做清空后重置，所以不做转换的字段名也要单独列出。如：
    //        vomapUtil.initShareKey(",=alias,driver-url=driverUrl,driver-class=driverClass");
    public VoMapUtil initShareKey(String skey){
        if(skey==null||skey.length()<3||skey.charAt(0)==skey.charAt(1)){
            __shareKeyMap = null;
            return this;
        }
        return initShareKey(getKeyFieldMap(skey));
    }

    //记录集合转换方法
    public VoMapUtil initRsMethod(Class rsClass,String methodName,Class argvClass){
        __rsMethod = null;
        if(methodName==null||methodName.equals(""))methodName="getString";
        Method mm = null;
        try{
            mm = rsClass.getMethod(methodName,argvClass);
        }catch(NoSuchMethodException e){}
        if(argvClass==null)argvClass = String.class;
        __rsMethod = mm;
        return this;
    }

    private HashMap<String,String> getKeyFieldMap(String skey){// ,=aa=bb,cc=dd
        HashMap<String,String> tmpFieldMap = new HashMap<String,String>();

        String sRight = skey.substring(2);
        String s00 = String.valueOf(skey.charAt(0));
        String s01 = String.valueOf(skey.charAt(1));
        String[] fDatas = sRight.split(s00);

        for(int i=0;i<fDatas.length;i++){
            String[] tmpDatas = fDatas[i].trim().split(s01);
            if(tmpDatas.length<=0)continue;
            String fData = tmpDatas[0].trim();
            if(tmpFieldMap.containsKey(fData))continue;

            String iData = fData;
            if(tmpDatas.length>=2){
                String iiData = tmpDatas[1].trim();
                if(iiData.length()>0)iData = iiData;
            }
            tmpFieldMap.put(fData,iData);
        }
        return tmpFieldMap;
    }

    private Map<String,Field> getVoFieldMap(Object vo){
        Class cls = vo.getClass();
        Map<String,Field> fMap = __vosFieldMap.get(cls);
        if(fMap==null){
            fMap = getFieldMap(cls);
            __vosFieldMap.put(cls,fMap);
        }
        return fMap;
    }

    //根据cls创建vo实例
    public static Object createVoObj(Class cls){
        try{
            return cls.newInstance();
        }catch(Exception e){
            return null;
        }
    }

    //生成map副本
    public Map getMapObj(Map map){
        return new HashMap(map);
    }

    //设置vo值
    public void setVoValue(Object vo,String name,Object value){
        // logger.debug(JsonUtil.toJson(new Object[]{"setVoValue",name,value}));
        if(vo==null)return;
        Field fff = getVoFieldMap(vo).get(name);
        if(fff==null)return;
        try{
            Object value1 = convertObject(fff.getType(),value);
            fff.set(vo,value1);
        }catch(Exception e){
            // logger.info("setVoValue==>" + e.toString());
        }
    }

    //得到vo值
    public Object getVoValue(Object vo,String name){
        if(vo==null)return null;
        Field fff = getVoFieldMap(vo).get(name);
        if(fff==null)return null;
        try{
            return fff.get(vo);
        }catch(Exception e){
            // logger.info("getVoValue==>" + e.toString());
            return null;
        }
    }

    //设置map值
    public void setMapValue(Map map,String name,Object value){
        if(map==null)return;
        Object value0 = map.get(name);
        Object value1 = value;
        try{
            value1 = convertObject(value0.getClass(),value);
        }catch(Exception e){}
        map.put(name,value1);
    }

    //得到map值
    public Object getMapValue(Map map,String name){
        if(map==null)return null;
        return map.get(name);
    }

    //得到rs值
    public Object getRsValue(Object rsx,String name){
        try{
            return __rsMethod.invoke(rsx,name);
        }catch(Exception e){
        }
        return null;
    }

    //public void copyVoToVo(List<Object> vo_0,List<Object> vo_1){
    //    Map<String,Field> fMap0 = getVoFieldMap(vo_0.get(0));
    //    Map<String,Field> fMap1 = getVoFieldMap(vo_1.get(0));
    //    if(fMap0==null||fMap1==null)return;

    //    if(__shareKeyMap==null){
    //        for(Map.Entry<String,Field> entry0 : fMap0.entrySet()){
    //            String key = entry0.getKey();
    //            for(int i=0;i<vo_0.size();i++){
    //                setVoValue(vo_1.get(i),key, getVoValue(vo_0.get(i),key));
    //            }
    //        }
    //        return;
    //    }

    //    for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
    //        String key = entry0.getKey();
    //        String key1 = entry0.getValue();

    //        for(int i=0;i<vo_0.size();i++){
    //            setVoValue(vo_1.get(i),key1, getVoValue(vo_0.get(i),key));
    //        }
    //    }
    //}

    //将一个vo拷到另一个vo
    public void copyVoToVo(Object vo_0,Object vo_1){
        Map<String,Field> fMap0 = getVoFieldMap(vo_0);
        Map<String,Field> fMap1 = getVoFieldMap(vo_1);
        if(fMap0==null||fMap1==null)return;

        if(__shareKeyMap==null){
            for(Map.Entry<String,Field> entry0 : fMap0.entrySet()){
                String key = entry0.getKey();
                setVoValue(vo_1,key, getVoValue(vo_0,key));
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setVoValue(vo_1,key1, getVoValue(vo_0,key));
        }
    }

    //将一个vo拷到另一个map
    public void copyVoToMap(Object vo,Map map){
        Map<String,Field> voMap = getVoFieldMap(vo);
        if(__shareKeyMap==null){
            for(Map.Entry<String,Field> entry : voMap.entrySet()){
                String key = entry.getKey();
                setMapValue(map,key,getVoValue(vo,key));
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setMapValue(map,key1,getVoValue(vo,key));
        }
    }

    //将一个map拷到另一个vo
    public void copyMapToVo(Map<String,Object> map,Object vo){
        if(map==null||map.size()<=0)return;

        if(__shareKeyMap==null){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                setVoValue(vo,entry.getKey(),entry.getValue());
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setVoValue(vo,key1,map.get(key));
        }
    }

    //将一个map拷到另一个map
    public void copyMapToMap(Map<String,Object> map0,Map<String,Object> map1){
        if(map0==null||map0.size()<=0)return;

        if(__shareKeyMap==null){
            for(Map.Entry<String,Object> entry : map0.entrySet()){
                setMapValue(map1,entry.getKey(),entry.getValue());
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setMapValue(map1,key1,map0.get(key));
        }
    }

    //将rs转化成vo或map,在于为copyRsToVo和copyRsToMap提供统一的接口
    public Object convertRs(Object rs,Object obj){
        if(obj.getClass()==Class.class){
            Object pvo = createVoObj((Class)obj);
            copyRsToVo(rs,pvo);
            return pvo;
        }
        Map map = getMapObj((Map)obj);
        copyRsToMap(rs,map);
        return map;
    }

//    //在于为copyVoToMap和copyMapToMap提供统一的接口
//    public Map<String,Object> convertToMap(Object obj){
//        if(obj.getClass()==HashMap.class){
//            copyMapToMap(obj,);
//
//
//            Object pvo = createVoObj((Class)obj);
//            copyRsToVo(rs,pvo);
//            return pvo;
//        }
//        Map map = getMapObj((Map)obj);
//        copyRsToMap(rs,map);
//        return map;
//    }

//    public Object convertRs(Object rs,Class cls){
//        Object pvo = createVoObj(cls);
//        copyRsToVo(rs,pvo);
//        return pvo;
//    }
//
//    public Object convertRs(Object rs,Map map){
//        Map pvo = new HashMap(map);
//        copyRsToMap(rs,pvo);
//        return pvo;
//    }

    //将一个rs拷到另一个vo
    public void copyRsToVo(Object rs,Object vo){
        if(__shareKeyMap==null){
            Map<String,Field> voMap = getVoFieldMap(vo);
            for(Map.Entry<String,Field> entry : voMap.entrySet()){
                String key = entry.getKey();
                Object val = getRsValue(rs,key);
                setVoValue(vo,key,val);
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setVoValue(vo,key1,getRsValue(rs,key));
        }
    }

    //将一个rs拷到另一个map
    public void copyRsToMap(Object rs,Map<String,Object> map){
        if(__shareKeyMap==null){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                String key = entry.getKey();
                setMapValue(map,key,getRsValue(rs,key));
            }
            return;
        }

        for(Map.Entry<String,String> entry0 : __shareKeyMap.entrySet()){
            String key = entry0.getKey();
            String key1 = entry0.getValue();
            setMapValue(map,key1,getRsValue(rs,key));
        }
    }

    //简单数据类型类的转换
    private static Class convertClass(Class cls){
        if(cls==String.class)return String.class;
        if(cls==int.class)return Integer.class;
        if(cls==char.class)return Character.class;
        if(cls==long.class)return Long.class;
        if(cls==float.class)return Float.class;
        if(cls==double.class)return Double.class;
        if(cls==boolean.class)return Boolean.class;
        // if(cls==java.sql.Timestamp.class)return Long.class;
        return cls;
    }

    //简单数据类型对象的转换
    private static Object convertObject(Class cls,Object oo){
        // if(cls==String.class&&"java.sql.Timestamp".equals(oo.getClass().getName()))return oo.toString();
        Class cls1 = convertClass(cls);
        if(cls1==String.class)return oo.toString();
        try{
            return cls1.getConstructor(String.class).newInstance(oo.toString());
        }catch(Exception e){
            // logger.info("class type==>" + cls.getName() + ",oo class type==>" + oo.getClass().getName());
            // logger.info("convertObject==>" + e.toString());
            return oo;
        }
    }

    //map是否相等
    public static boolean checkMapEquals(Map<String,Object> mm0,Map<String,Object> mm1){
        if(mm0==null||mm0.size()<=0)return false;
        if(mm1==null||mm1.size()<=0)return false;
        if(mm0.size()!=mm1.size())return false;
        for(Map.Entry<String,Object> entry0 : mm0.entrySet()){
            String key = entry0.getKey();
            Object oo0 = entry0.getValue();
            Object oo1 = mm1.get(key);
            if(!checkBasicValueEquals(oo0,oo1))return false;
        }

        return true;
    }

    //基本数据是否相等
    public static boolean checkBasicValueEquals(Object oo0,Object oo1){
        Class cls0 = convertClass(oo0.getClass());
        Class cls1 = convertClass(oo1.getClass());
        if(cls0!=cls1)return false;

        if(oo0==null&&oo1==null)return true;
        if(oo0==null||oo1==null)return false;
        return oo0.toString().equals(oo1.toString());
    }

    public <T> void copyMapToVo(List<Map<String,Object>> maplist,List<T> volist,Class voCls){
        if(maplist==null)return;
        for(Map<String,Object> map : maplist){
            T pvo = (T)createVoObj(voCls);
            copyMapToVo(map,pvo);
            volist.add(pvo);
        }
    }

    public void copyMapToMap(List<Map<String,Object>> maplist,List<Map<String,Object>> maplist1){
        if(maplist==null)return;
        for(Map<String,Object> map : maplist){
            Map<String,Object> map1 = new HashMap<String,Object>();
            copyMapToMap(map,map1);
            maplist1.add(map1);
        }
    }
    public static void main(String[] args){
    }
}

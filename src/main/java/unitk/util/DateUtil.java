package unitk.util;
import java.util.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
public final class DateUtil{
    private DateUtil(){}
    private final static DateUtil __instance = new DateUtil();
    public static DateUtil getInstance(){
        return __instance;
    }

    private static Date __mindate = Timestamp.valueOf("1000-01-01 00:00:00.0");
    //空或异常时，返回默认值;bthrow异常时是否抛出
    private <T> T toData(Object oo,T cc,boolean isthrow){
        return DataUtil.getInstance().toData(oo,cc,isthrow);
    }

    private String packTimeNumber(Object... args){
        if(args==null||args.length<=0)return "";
        StringBuilder buf = new StringBuilder(1024);
        for(Object obj : args){
            if(obj instanceof Integer){
                int nnn = Integer.parseInt(obj.toString());
                if(nnn<10)buf.append(0);
            }
            buf.append(obj);
        }
        return buf.toString();
    }

    public long getTime(Object oo,boolean isthrow){
        if(oo==null)return 0;
        if(oo instanceof Date){
            Date ttt = (Date)oo;
            return ttt.getTime();
        }
        if(oo instanceof Number){
            return toData(oo,0l,isthrow);
        }
        if(oo instanceof String){
            return getTime00((String)oo,isthrow);
        }
        return 0;
    }

    private long getTime00(String oo,boolean isthrow){
        if(oo==null||oo.length()<=0)return 0;
        String ssss = oo.trim().replaceAll("[^0-9]+",",");
        String[] ss = ssss.split(",");
        int[] dd = new int[]{1000,1,1,0,0,0,0};
        int[] min = new int[]{1000,1,1,0,0,0,0};
        int[] max = new int[]{9999,12,31,59,59,59,999};//999999

        for(int i=0;i<ss.length&&i<min.length;i++){
            dd[i]=toData(ss[i],0,isthrow);
            if(dd[i]<min[i]||dd[i]>max[i]){
                if(isthrow)throw new RuntimeException("时间转换异常:" + i + "," + dd[i]);
                return 0;
            }
        }

        String sss = packTimeNumber(dd[0],"-",dd[1],"-",dd[2]," ",dd[3],":",dd[4],":",dd[5],".",dd[6]);
        try{
            return java.sql.Timestamp.valueOf(sss).getTime();
        }catch(Exception err){
            if(isthrow)throw new RuntimeException("时间(" + sss + ")转换异常",err);
        }
        return 0;
    }




    //字符转日期
    public Date toDate(CharSequence oo,boolean isthrow){
        if(oo==null)return null;

        String ssss = oo.toString().trim().replaceAll("[^0-9]+",",");
        String[] ss = ssss.split(",");
        int[] dd = new int[]{1000,1,1,0,0,0,0};
        int[] min = new int[]{1000,1,1,0,0,0,0};
        int[] max = new int[]{9999,12,31,59,59,59,999};//999999

        for(int i=0;i<ss.length&&i<min.length;i++){
            dd[i]=toData(ss[i],0,isthrow);
            if(dd[i]<min[i]||dd[i]>max[i]){
                if(isthrow)throw new RuntimeException("时间转换异常:" + i + "," + dd[i]);
                return __mindate;
            }
        }

        String sss = packTimeNumber(dd[0],"-",dd[1],"-",dd[2]," ",dd[3],":",dd[4],":",dd[5],".",dd[6]);
        try{
            return java.sql.Timestamp.valueOf(sss);
        }catch(Exception err){
            if(isthrow)throw new RuntimeException("时间(" + sss + ")转换异常",err);
            return __mindate;
        }
    }
    //数字转日期
    public Date toDate(Number oo,boolean isthrow){
        long ltime = toData(oo,0l,isthrow);
        try{
            // return new Date(ltime);
            return new Timestamp(ltime);
        }catch(Exception err){
            if(isthrow)throw new RuntimeException("时间long(" + ltime + ")转换异常",err);
            return __mindate;
        }
    }
    //yyyy-MM-dd HH:mm:ss.SSS
    public String format(Date oo,String panel){
        SimpleDateFormat dateformatter = new SimpleDateFormat(panel);
        return dateformatter.format(oo);
    }
    public Date add(Date src,int field,int amount){
        Calendar cc = Calendar.getInstance();
        cc.setTime(src);
        cc.add(field,amount);
        return cc.getTime();
    }
}

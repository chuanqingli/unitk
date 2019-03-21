package unitk.util;

import java.util.*;
/**分页相关
javac -Xlint:unchecked *.java
 */
public final class PageUtil{

    private PageUtil(){}
    private final static PageUtil __instance = new PageUtil();
    public static PageUtil getInstance(){
        return __instance;
    }


    //得到总页数
    public static int getPageCount(int dataCount,int pageSize){
        if(dataCount<=0||pageSize<=0)return -1;
        return (dataCount+pageSize-1)/pageSize;
        // int pageCount = dataCount/pageSize;
        // int lastSize = dataCount%pageSize;
        // if(lastSize>0)pageCount += 1;
        // return pageCount;
    }

    //得到最后一页容量
    public static int getLastSize(int dataCount,int pageSize){
        if(dataCount<=0||pageSize<=0)return -1;
        return dataCount%pageSize;
    }

    public static List<Integer> getPageSizeList(int dataCount,int pageCount){
        List<Integer> resp = new ArrayList<Integer>();
        if(dataCount<=0||pageCount<=0)return resp;
        int pert = dataCount/pageCount;
        int left = dataCount%pageCount;
        for(int i=0;i<pageCount;i++){
            resp.add(pert);
        }
        for(int i=0;i<left;i++){
            resp.set(i,resp.get(i)+1);
        }
        return resp;
     }

}

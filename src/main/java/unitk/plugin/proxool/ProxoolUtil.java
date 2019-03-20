package unitk.plugin.proxool;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;

import unitk.util.*;
/*
javac -encoding utf-8 VoMapUtil.java
java VoMapUtil
*/
public final class ProxoolUtil{

    private final static ProxoolUtil __instance = new ProxoolUtil();
    private static final Map<String,ProxoolBean> __proxoolmap = __instance.getProxoolMap00();

    private ProxoolUtil(){}

    public static ProxoolUtil getInstance(){
        return __instance;
    }

    private class ProxoolHandler extends SAXParserHandler{
        public ProxoolHandler(String qname){
            super(qname);
        }
        public void doWithAttributes(String qName, Attributes attributes){
            if("property".equals(qName)&&attributes.getLength()==2&&attributes.getQName(0).equals("name")&&attributes.getQName(1).equals("value")){
                __item.put(attributes.getValue(0),attributes.getValue(1));
            }
        }
    }

    protected List<Map<String,Object>> getList(){
        String path = "/config/proxool.xml";

       SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParserHandler handler = new ProxoolHandler("proxool");
        //2.通过factory的newSAXParser()方法获取一个SAXParser类的对象。
        try {
        String path2 = this.getClass().getResource(path).toURI().toString();
        // logger.info("path2==>" + path2);
            SAXParser parser = factory.newSAXParser();
            //创建SAXParserHandler对象
            parser.parse(path2, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String,Object>> list = handler.getList();
        return list;
    }

    private List<ProxoolBean> getProxoolList(){
        List<Map<String,Object>> list = getList();
        VoMapUtil vomapUtil = new VoMapUtil();
        vomapUtil.initShareKey(",=alias,driver-url=driverUrl,driver-class=driverClass,user=username,password");
        List<ProxoolBean> list2 = new ArrayList<ProxoolBean>();
        vomapUtil.copyMapToVo(list,list2,ProxoolBean.class);
        return list2;
    }

    public Map<String,ProxoolBean> getProxoolMap(){
        return __proxoolmap;
    }

    private Map<String,ProxoolBean> getProxoolMap00(){
        List<ProxoolBean> proxoollist = getProxoolList();
        if(proxoollist==null||proxoollist.size()<=0)return null;
        Map<String,ProxoolBean> resp = new java.util.concurrent.ConcurrentHashMap<String,ProxoolBean>();
        for(int i=0;i<proxoollist.size();i++){
            ProxoolBean ppp = proxoollist.get(i);
            resp.put(ppp.alias,ppp);
        }
        return resp;
    }

}

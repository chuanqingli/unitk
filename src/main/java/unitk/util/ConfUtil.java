package unitk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public final class ConfUtil {

    private final static ConfUtil __instance = new ConfUtil();
    private ConfUtil(){}
    public final static ConfUtil getInstance(){
        return __instance;
    }

    private final static Properties _prop = getProperties();

    /**
     * 初始化
     */
    private static Properties getProperties(){
        Properties prop = new Properties();
        try {
            // InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("classpath:config/unitk.conf");
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/unitk.conf");

            // InputStream in = ConfUtil.class.getResourceAsStream("/config/unitk.conf");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));
            prop.load(bf);
        }catch (Exception e){
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return
     */
    public String getProperty(String key){
        return _prop.getProperty(key);
    }

    public String toString(){
        return _prop.toString();
    }

    public <T> T getBean(Class<T> s){
        String key = s.getName();
        Class val = null;
        try{
            val = Class.forName(getProperty(key));
            return (T)val.newInstance();
        }catch(Exception err){
            throw new RuntimeException(err);
        }

    }
}
package unitk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public final class PropertiesUtil {
    /**
     * 初始化
     参数必须是包路径+文件名+.后缀 否则会报空指针异常
     */
    public static Properties getProperties(String path){
        Properties prop = new Properties();
        try {
            //getResourceAsStream(name)方法的参数必须是包路径+文件名+.后缀 否则会报空指针异常
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));
            prop.load(bf);
            in.close();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return prop;
    }
}

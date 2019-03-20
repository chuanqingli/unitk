package unitk.plugins.springframework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// import org.springframework.core.io.*;

public final class BeanUtil implements unitk.util.BeanUtil{

    private BeanUtil(){}
    private final static unitk.util.BeanUtil __instance = new BeanUtil();
    public static unitk.util.BeanUtil getInstance(){
        return __instance;
    }

    private static final ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:config/spring.xml");
    // private static final Resource __resource = new ClassPathResource("config/mybatis.cfg.xml");

    public <T> T getBean(Class<T> s){
        return appContext.getBean(s);
    }

    public <T> T getBean(String name,Class<T> s){
        return appContext.getBean(name,s);
    }


    public Object getBean(String name,Object... args){
        return appContext.getBean(name,args);
    }
}

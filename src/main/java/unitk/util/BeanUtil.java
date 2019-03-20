package unitk.util;

public interface BeanUtil{
    public static BeanUtil getInstance(){
        return unitk.plugins.springframework.BeanUtil.getInstance();
    }

    <T> T getBean(Class<T> s);
    <T> T getBean(String name,Class<T> s);
    Object getBean(String name,Object... args);
}

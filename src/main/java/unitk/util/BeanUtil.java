package unitk.util;

public interface BeanUtil{
    public final static BeanUtil __instance = ConfUtil.getInstance().getBean(BeanUtil.class);
    public static BeanUtil getInstance(){
        return __instance;
    }

    <T> T getBean(Class<T> s);
    <T> T getBean(String name,Class<T> s);
    Object getBean(String name,Object... args);
}

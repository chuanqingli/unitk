package unitk.util;

public interface JsonUtil{

    public final static JsonUtil __instance = ConfUtil.getInstance().getBean(JsonUtil.class);
    public static JsonUtil getInstance(){
        return __instance;
    }

    String toJson(Object bean);
    <T> T toBean(String json,Class<T> cls);
}

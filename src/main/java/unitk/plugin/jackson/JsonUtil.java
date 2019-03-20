package unitk.plugin.jackson;

import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;

public final class JsonUtil implements unitk.util.JsonUtil{
    private static ObjectMapper mapper = new ObjectMapper();

    public String toJson(Object bean){
        try {
            return mapper.writeValueAsString(bean);
        } catch (Exception ex) {
            throw new RuntimeException("java bean转成json字符串异常", ex);
        }
    }

    public <T> T toBean(String json,Class<T> cls){
        try {
            return mapper.readValue(json,cls);
        }catch (Exception e){
            throw new RuntimeException("json字符串转成java bean(" + cls.getSimpleName() + ")异常", e);
        }
    }
}

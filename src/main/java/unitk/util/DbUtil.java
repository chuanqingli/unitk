package unitk.util;

import java.util.*;

public interface DbUtil{

    public final static DbUtil __instance = ConfUtil.getInstance().getBean(DbUtil.class);
    public static DbUtil getInstance(){
        return __instance;
    }

    List<Map<String,Object>> selectList(Map<String,Object> map);
}

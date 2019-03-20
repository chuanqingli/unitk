package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import java.util.*;

public class JsonUtilTests {

	@Test
	public void getBean() {
        Map<String,Object> map = MapUtil.getInstance().create(new HashMap<String,Object>(),new String[]{"aaa","bbb"},new Object[]{"你好",12345l});

        JsonUtil util = JsonUtil.getInstance();
        String sss = util.toJson(map);
        Map<String,Object> map2 = util.toBean(sss,HashMap.class);
        System.out.println(sss);
        System.out.println(map2);

	}

}

package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import unitk.util.*;
import java.util.*;

public class CollectionUtilTests {

        CollectionUtil util = CollectionUtil.getInstance();
	// @Test
	public void create() {

        Set<Integer> ttt = util.create(new HashSet<Integer>(),23,45,67);
        Set<Integer> ttt1 = util.create(new HashSet<Integer>(),0,23,45,67,"999");
        
        System.out.println("你好啊bbb" + ttt + ttt1);
        System.out.println(Arrays.asList("12","345","erwer"));
	}

}

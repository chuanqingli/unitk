package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import java.util.*;

public class ConfUtilTests {

	@Test
	public void getBean() {

        ConfUtil util = ConfUtil.getInstance();
        System.out.println(util.toString());
        System.out.println(JsonUtil.class.getName());

	}

}

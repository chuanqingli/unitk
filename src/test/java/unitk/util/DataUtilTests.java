package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import unitk.util.*;
import java.util.*;

public class DataUtilTests {

	@Test
	public void sqlValue() {

        Map<String,Object> map = MapUtil.getInstance().create(new HashMap<String,Object>(),new String[]{"aa","bb","cc","dd"},new Object[]{"21312",134,345l,new Date()});
        for(Map.Entry<String,Object> entry : map.entrySet()){
            System.out.println(entry.getKey() + "," + entry.getValue() + "," + DataUtil.getInstance().sqlValue(entry.getValue()));
        }
        
	}

    

}

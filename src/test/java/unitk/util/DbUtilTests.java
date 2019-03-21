package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import java.util.*;

public class DbUtilTests {

	@Test
	public void getBean() {

        DbUtil util = DbUtil.getInstance();
        Map<String,Object> map = MapUtil.getInstance().create(new HashMap<String,Object>(),new String[]{"_dbid","_sql"},new Object[]{"tianya_ebook","select * from tyf_reward_transfer_resp"});

        List<Map<String,Object>> resp = util.selectList(map);
        System.out.println("MyBatisDao==>" + resp);
	}

}

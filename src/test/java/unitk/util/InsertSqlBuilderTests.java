package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import unitk.util.*;
import java.util.*;

public class InsertSqlBuilderTests {

	@Test
	public void getSql() {

        InsertSqlBuilder tt = InsertSqlBuilder.getInstance("dfs");

        String[] ss = new String[]{"aa","bb","cc","dd"};
        // List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        // CollectionUtil.getInstance().create(list,);
        
        Map<String,Object> map = new HashMap<String,Object>();
        tt.unionall().insert("aa")
            .values(MapUtil.getInstance().create(new HashMap<String,Object>(),ss,new Object[]{"21312",134,345l,new Date()}))
            .values(MapUtil.getInstance().create(new HashMap<String,Object>(),ss,new Object[]{"你好",135,345l,new Date()}))
        .values(MapUtil.getInstance().create(new HashMap<String,Object>(),ss,new Object[]{"海南",136,345l,new Date()}));
        tt.values(MapUtil.getInstance().create(new HashMap<String,Object>(),ss,new Object[]{"海南",137,896l,new Date()}));

        System.out.println(tt.getSql());
     
	}

    

}

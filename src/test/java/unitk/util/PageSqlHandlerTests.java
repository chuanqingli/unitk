package unitk.util;

import org.junit.Test;
// import org.junit.runner.RunWith;

import java.util.*;

public class PageSqlHandlerTests {

	@Test
	public void getBean() {
        // Set<Class> set = PackageUtil.getInstance().getImplSet(PageSqlHandler.class);
        // System.out.println(set);
        String s1 = PageSqlHandler.getInstance("sqlserver").getSql("select * from aaa","order by cc asc,dd desc",5,10);
        String s2 = PageSqlHandler.getInstance("mysql").getSql("select * from aaa","order by cc asc,dd desc",5,10);
        System.out.println(s1);
        System.out.println(s2);
	}

}

package unitk.plugin.proxool;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import java.util.*;
/**自定义的数据源*/
public class CustomProxoolDataSource extends ProxoolDataSource{
 public void setAliasName(String aas) {
     String alias = aas;
     Map<String,ProxoolBean> map = ProxoolUtil.getInstance().getProxoolMap();
     ProxoolBean value = map.get(alias);
     this.setDriver(value.driverClass);
     this.setDriverUrl(value.driverUrl);
     this.setUser(value.username);
     this.setPassword(value.password);
     this.setAlias(alias);


// jdbc-3.proxool.alias=tybbs_monitor__m
// jdbc-3.proxool.maximum-active-time=300000
// jdbc-3.proxool.maximum-connection-lifetime=300000
// jdbc-3.proxool.driver-url=jdbc:mysql://xxx:3306/ty_app_monitor?useUnicode=true&amp;characterEncoding=utf-8
// jdbc-3.proxool.driver-class=com.mysql.jdbc.Driver
// jdbc-3.user=xxx
// jdbc-3.password=xxx
// jdbc-3.proxool.maximum-connection-count=20
// jdbc-3.proxool.simultaneous-build-throttle=12
// jdbc-3.proxool.minimum-connection-count=1
// jdbc-3.proxool.prototype-count=1
// jdbc-3.proxool.house-keeping-test-sql=SELECT 0


    //    <house-keeping-sleep-time>30000</house-keeping-sleep-time>
    // <house-keeping-test-sql>select 0</house-keeping-test-sql>
    // <maximum-connection-count>100</maximum-connection-count>
    // <minimum-connection-count>5</minimum-connection-count>
    // <maximum-connection-lifetime>1440000</maximum-connection-lifetime>
    // <simultaneous-build-throttle>40</simultaneous-build-throttle>
    // <recently-started-threshold>60000</recently-started-threshold>
    // <overload-without-refusal-lifetime>60000</overload-without-refusal-lifetime>
    // <maximum-active-time>900000</maximum-active-time>
    // <prototype-count>1</prototype-count>
    // <test-before-use>true</test-before-use>
     
     //如果发现了空闲的数据库连接.house keeper 将会用这个语句来测试.这个语句最好非常快的被执行.如果没有定义,测试过程将会被忽略。
     this.setHouseKeepingTestSql("select 0");
     //自动侦察各个连接状态的时间间隔(毫秒),侦察到空闲的连接就马上回收,超时的销毁 默认30秒）
     this.setHouseKeepingSleepTime(30000);
     //最大活动时间
     this.setMaximumActiveTime(900000);
     //一个线程的最大寿命.
     this.setMaximumConnectionLifetime(1440000);
     //最大的数据库连接数.一般的大应用设置30就足够了。
     this.setMaximumConnectionCount(100);
     //最小的数据库连接数，一般最好事先初始化一部分连接这样，对于初次连接数据库的应用效率比较高，推荐设置5－10
     this.setMinimumConnectionCount(5);
     //最少保持的空闲连接数 （默认2个）
     this.setPrototypeCount(1);
     //这是我们可一次建立的最大连接数。那就是新增的连接请求,但还没有可供使用的连接。由于
     //连接可以使用多线程,在有限的时间之间建立联系从而带来可用连接，但是我们需要通过一些方式确认一些线程并不是立即响应
     //连接请求的，默认是10。
     this.setSimultaneousBuildThrottle(40);
     this.setTestBeforeUse(true);
     //这可以帮助我们确定连接池的状态,连接数少还是多或超载。只要至少有一个连接
     //已开始在此值(毫秒)内,或者有一些多余的可用连接,那么我们假设连接池是开启的。默认为60秒
     this.setRecentlyStartedThreshold (60000);
     //这可以帮助我们确定连接池的状态。如果我们已经拒绝了一个连接在这个设定值
// (毫秒),然后被认为是超载。默认为60秒。
     this.setOverloadWithoutRefusalLifetime(60000);
     this.setTrace(true);
 }


    
}

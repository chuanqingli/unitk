package unitk.plugin;
/**
数据源切换开关
 */
public final class DataSourceSwitch{
    private static final ThreadLocal<String> contextHolder=new ThreadLocal<String>();

    public static void setDataSourceType(String dataSourceType){
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType(){
        return (String) contextHolder.get();
    }

    public static void clearDataSourceType(){
        contextHolder.remove();
    }
}

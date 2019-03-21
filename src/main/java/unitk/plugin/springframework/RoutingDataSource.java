package unitk.plugin.springframework;
import unitk.plugin.DataSourceSwitch;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  
  
public class RoutingDataSource extends AbstractRoutingDataSource {  
    @Override  
    protected Object determineCurrentLookupKey() {  
         return DataSourceSwitch.getDataSourceType();    
    }  
}  

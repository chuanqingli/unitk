package unitk.plugins.springframework;
import unitk.util.DataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  
  
public class RoutingDataSource extends AbstractRoutingDataSource {  
    @Override  
    protected Object determineCurrentLookupKey() {  
         return DataSourceHolder.getDataSourceType();    
    }  
}  

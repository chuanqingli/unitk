package unitk.util;
import unitk.vo.SqlBean;
final class SqlUtil_mysql extends SqlUtil.in{
    protected String getPageSql_unisql(String unisql,String order,int skipsize,int pagesize){
        if(pagesize>0){
            if(skipsize<=0){
                return wrap("select * from (",unisql,") _t0 ",order," limit ",pagesize);
            }

            return wrap("select * from (",unisql,") _t0 ",order," limit ",skipsize,",",pagesize);
        }
        return wrap("select * from (",unisql,") _t0 ",order);
    }

    protected String getPageSql_table(SqlBean b){
        String sql0 = wrap("select ",b._fields," from ",b._table," ",b._where," ",b._group," ",b._order);
        if(b._pagesize>0){
            if(b._skipsize<=0){
                return wrap(sql0," limit ",b._pagesize);
            }

            return wrap(sql0," limit ",b._skipsize,",",b._pagesize);
        }

        return sql0;
    }
}

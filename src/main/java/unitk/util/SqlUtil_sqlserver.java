package unitk.util;
import unitk.vo.SqlBean;
final class SqlUtil_sqlserver extends SqlUtil.in{
    protected String getPageSql_unisql(String unisql,String order,int skipsize,int pagesize){
        if(pagesize>0){
            if(skipsize<=0){
                return wrap("select top ",pagesize," * from (",unisql,") _t0 ",order);
            }

            String unisql0 = wrap("select *,row_number() over(",order,") _rownum from (",unisql,") _t00");
            return wrap("select * from (",unisql0,") _t0 where _t0._rownum>",skipsize," and _t0._rownum<=",skipsize+pagesize);
        }
        return wrap("select * from (",unisql,") _t0 ",order);
    }

    protected String getPageSql_table(SqlBean b){
        if(b._pagesize>0){
            if(b._skipsize<=0){
                return wrap("select top ",b._pagesize," ",b._fields
                                           ," from ",b._table," ",b._where," ",b._group," ",b._order);
            }

            String sql0 = wrap("select ",b._fields,",row_number() over(",b._order,") _rownum from ",b._table," ",b._where);
            return wrap("select * from (",sql0,") _t0 where _t0._rownum>",b._skipsize," and _t0._rownum<=",b._skipsize+b._pagesize);
        }

        return wrap("select ",b._fields," from ",b._table," ",b._where," ",b._group," ",b._order);
    }
}

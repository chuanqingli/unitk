package unitk.util;
final class SqlUtil_sqlserver implements SqlUtil{
    public String wrap(Object... o){
        return StringUtil.getInstance().wrap(new StringBuffer(1024),"","",o).toString();
    }
    public String getPageSql(String unisql,String order,int skipsize,int pagesize){
        if(pagesize>0){
            if(skipsize<=0){
                return wrap("select top ",pagesize," * from (",unisql,") _t0 ",order);
            }

            String unisql0 = wrap("select *,row_number() over(",order,") _rownum from (",unisql,") _t00");
            return wrap("select * from (",unisql0,") _t0 where _t0._rownum>",skipsize," and _t0._rownum<=",skipsize+pagesize);
        }
        return wrap("select * from (",unisql,") _t0 ",order);
    }
}

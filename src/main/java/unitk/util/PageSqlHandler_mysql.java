package unitk.util;
final class PageSqlHandler_mysql implements PageSqlHandler{
    public String wrap(Object... o){
        return StringUtil.getInstance().wrap(new StringBuffer(1024),"","",o).toString();
    }
    public String getSql(String unisql,String order,int skipsize,int pagesize){
        if(pagesize>0){
            if(skipsize<=0){
                return wrap("select * from (",unisql,") _t0 ",order," limit ",pagesize);
            }

            return wrap("select * from (",unisql,") _t0 ",order," limit ",skipsize,",",pagesize);
        }
        return wrap("select * from (",unisql,") _t0 ",order);
    }
}

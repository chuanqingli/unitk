package unitk.util;
import java.util.*;
import unitk.vo.SqlBean;
public final class SqlBuilder{
    private SqlBean _b = new SqlBean();
    public SqlBuilder dbid(String s){_b._dbid = s;return this;}
    public SqlBuilder dbroute(String s){_b._dbroute = s;return this;}
    public SqlBuilder table(String s){_b._table = s;return this;}
    public SqlBuilder fields(String s){_b._fields = s;return this;}
    public SqlBuilder group(String s){_b._group = s;return this;}
    public SqlBuilder where(String s){_b._where = s;return this;}
    public SqlBuilder order(String s){_b._order = s;return this;}
    public SqlBuilder skipsize(int s){_b._skipsize = s;return this;}
    public SqlBuilder pagesize(int s){_b._pagesize = s;return this;}
    public SqlBuilder key(String s){_b._key = s;return this;}
    public SqlBuilder sql(String s){_b._sql = s;return this;}
    public SqlBuilder unisql(String s){_b._unisql = s;return this;}
    public SqlBuilder method(String s){_b._method = s;return this;}
    public SqlBuilder values(String s){_b._values = s;return this;}
    public SqlBuilder sets(String s){_b._sets = s;return this;}
    public SqlBuilder select(String s){return method("select").table(s);}
    public SqlBuilder delete(String s){return method("delete").table(s);}
    public SqlBuilder update(String s){return method("update").table(s);}
    public SqlBuilder replace(String s){return method("replace").table(s);}
    public SqlBuilder insert(String s){return method("insert").table(s);}

    public SqlBuilder init(SqlBean b){_b = b;return this;}

    private void chkstrkey(String key,String name){
        if(key==null||key.length()<=0)throw new RuntimeException(name + "未赋值");
    }

    private boolean chkkey(String key){
        return (key!=null&&key.length()>0);
    }

    protected String wrap(Object... o){
        return StringUtil.getInstance().wrap(new StringBuffer(1024),"","",o).toString();
    }

    public String getSql(){
        chkstrkey(_b._dbid,"dbid");
        if(chkkey(_b._sql))return _b._sql;
        if("select".equals(_b._method)){
            return SqlUtil.getInstance(_b._dbroute).getPageSql(_b);
        }

        if("delete".equals(_b._method)){
            return wrap(_b._method," from ",_b._table," ",(chkkey(_b._where))?wrap(" where ",_b._where):"");
        }


        return "";
    }
}

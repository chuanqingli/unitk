package unitk.util;
import java.util.*;
public interface InsertSqlBuilder{
    static InsertSqlBuilder getInstance(String dbtype){
        return new in();//in.__map.get(dbtype);
    }

    InsertSqlBuilder insert(String t);
    InsertSqlBuilder values(List<Map<String,Object>> t);
    InsertSqlBuilder values(Map<String,Object> t);
    InsertSqlBuilder unionall();
    InsertSqlBuilder morevalues();
    String getSql();

    class in implements InsertSqlBuilder{
        class _Bean{
            String table;
            List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
            boolean morevalues = true;//是否支持morevalues，不支持需要改用select 字段集 union all 的方式
        }

        protected _Bean _b = new _Bean();
        public final InsertSqlBuilder insert(String t){
            _b.table = t;
            return this;
        }

        public final InsertSqlBuilder values(List<Map<String,Object>> t){
            _b.values.addAll(t);
            return this;
        }

        public final InsertSqlBuilder values(Map<String,Object> t){
            _b.values.add(t);
            return this;
        }

        public final InsertSqlBuilder unionall(){
            _b.morevalues=false;
            return this;
        }

        public final InsertSqlBuilder morevalues(){
            _b.morevalues=true;
            return this;
        }

        private void chkkey(String key,String name){
            if(key==null||key.length()<=0)throw new RuntimeException(name + "未赋值");
        }

        private void chkkey(List<Map<String,Object>> key,String name){
            if(key==null||key.size()<=0)throw new RuntimeException(name + "未赋值");
        }

        private void chkkey(Map<String,Object> key,String name){
            if(key==null||key.size()<=0)throw new RuntimeException(name + "未赋值");
        }
        protected StringBuilder wrap(StringBuilder buf,Object... o){
            return StringUtil.getInstance().wrap(buf,"","",o);
        }


        private StringBuilder getSql_morevalues(StringBuilder buf,Set<String> set0){
            int index=0;
            for(Map<String,Object> map : _b.values){
                Set<String> itemset = map.keySet();
                if(!itemset.containsAll(set0)){
                    Set<String> diffset = new TreeSet(set0);
                    diffset.removeAll(itemset);
                    throw new RuntimeException("values[" + index + "]赋值字段数不足,缺少" + diffset);
                }

                StringBuilder buf0 = new StringBuilder(1024);
                for(String key : set0){
                    wrap(buf0,",",DataUtil.getInstance().sqlValue(map.get(key)));
                }
                buf0.delete(0,1);
                wrap(buf,",(",buf0,")");
                index++;
            }
            buf.delete(0,1);
            return buf;
        }

        public String getSql(){
            chkkey(_b.table,"table");
            chkkey(_b.values,"values");

            Map<String,Object> map0 = _b.values.get(0);
            chkkey(map0,"values[0]");

            Set<String> set0 = new TreeSet(map0.keySet());

            StringBuilder buf = new StringBuilder(1024);
            StringUtil.getInstance().wrap(buf,",","",set0);
            buf.delete(0,1);
            String sql0 = buf.toString();

            buf.setLength(0);
            int index=0;

            String buf0str = null;//第一个buf的缓存
            for(Map<String,Object> map : _b.values){
                Set<String> itemset = map.keySet();
                if(!itemset.containsAll(set0)){
                    Set<String> diffset = new TreeSet(set0);
                    diffset.removeAll(itemset);
                    throw new RuntimeException("values[" + index + "]赋值字段数不足,缺少" + diffset);
                }

                StringBuilder buf0 = new StringBuilder(1024);
                for(String key : set0){
                    wrap(buf0,",",DataUtil.getInstance().sqlValue(map.get(key)));
                }
                buf0.delete(0,1);

                buf0str=buf0.toString();//第一个buf的缓存
                if(_b.morevalues){//支持morevalues
                    wrap(buf,",(",buf0,")");
                }else{
                    wrap(buf," union all select ",buf0);
                }
                index++;
            }

            if(!_b.morevalues){
                if(index>1){
                    buf.delete(0," union all ".length());
                    String sql1 = buf.toString();

                    buf.setLength(0);
                    wrap(buf,"insert into ",_b.table,"(",sql0,")",sql1);
                    return buf.toString();
                }

                buf.setLength(0);
                wrap(buf,",(",buf0str,")");//reback原有的值
            }

            buf.delete(0,1);
            String sql1 = buf.toString();

            buf.setLength(0);
            wrap(buf,"insert into ",_b.table,"(",sql0,")values",sql1);
            return buf.toString();
        }
    }
}

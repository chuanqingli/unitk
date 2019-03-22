package unitk.util;
import java.util.*;
public interface UpdateSqlBuilder{
    static UpdateSqlBuilder getInstance(boolean morevalues){
        in resp = new in();
        //不支持morevalues
        if(!morevalues)resp.unionall();
        return resp;
    }

    static UpdateSqlBuilder getInstance(){
        return new in();
    }

    UpdateSqlBuilder insert(String t);
    UpdateSqlBuilder fields(String... t);
    UpdateSqlBuilder fields(Collection t);
    UpdateSqlBuilder values(Collection t);
    UpdateSqlBuilder values(Map<String,Object> t);
    UpdateSqlBuilder values(Object... t);
    String getSql();

    class in implements UpdateSqlBuilder{
        class _Bean{
            String table;
            List values = new ArrayList();
            boolean morevalues = true;//是否支持morevalues，不支持需要改用select 字段集 union all 的方式
            Set<String> fields = new LinkedHashSet<String>();
        }

        protected _Bean _b = new _Bean();
        public final UpdateSqlBuilder insert(String t){
            _b.table = t;
            return this;
        }

        public final UpdateSqlBuilder fields(Collection t){
            _b.fields.addAll(t);
            return this;
        }

        public final UpdateSqlBuilder fields(String... t){
            return fields(Arrays.asList(t));
        }

        public final UpdateSqlBuilder values(Collection t){
            _b.values.addAll(t);
            return this;
        }

        public final UpdateSqlBuilder values(Map<String,Object> t){
            _b.values.add(t);
            return this;
        }

        public final UpdateSqlBuilder values(Object... t){
            return values(Arrays.asList(t));
        }

        private final UpdateSqlBuilder unionall(){
            _b.morevalues=false;
            return this;
        }

        private void chkkey(Object key,String name){
            if(key==null)throw new RuntimeException(name + "未赋值");
        }


        private void chkkey(String key,String name){
            if(key==null||key.length()<=0)throw new RuntimeException(name + "未赋值");
        }

        private void chkkey(Collection key,String name){
            if(key==null||key.size()<=0)throw new RuntimeException(name + "未赋值");
        }

        private void chkkey(Map key,String name){
            if(key==null||key.size()<=0)throw new RuntimeException(name + "未赋值");
        }
        protected StringBuilder wrap(StringBuilder buf,Object... o){
            return StringUtil.getInstance().wrap(buf,"","",o);
        }


        public String getSql(){
            chkkey(_b.table,"table");
            chkkey(_b.values,"values");
            chkkey(_b.fields,"fields");

            Set<String> set0 = _b.fields;

            StringBuilder buf = new StringBuilder(1024);
            StringUtil.getInstance().wrap(buf,",","",set0);
            buf.delete(0,1);
            String sql0 = buf.toString();

            buf.setLength(0);
            int index=0;

            String buf0str = null;//第一个buf的缓存
            for(Object obj : _b.values){
                chkkey(obj,"values[" + index + "]");
                List<Object> tmplist = new ArrayList<Object>();
                if(obj instanceof Map){
                    Map<String,Object> map = (Map<String,Object>)obj;
                    chkkey(map,"values[" + index + "]");
                    Set<String> itemset = map.keySet();
                    if(!itemset.containsAll(set0)){
                        Set<String> diffset = new LinkedHashSet(set0);
                        diffset.removeAll(itemset);
                        throw new RuntimeException("values[" + index + "]赋值字段数不足,缺少" + diffset);
                    }

                    for(String key : set0){
                        tmplist.add(map.get(key));
                    }
                }else if(obj instanceof List){
                    tmplist = new ArrayList((List)obj);
                }else{
                    throw new RuntimeException("values[" + index + "]不能转换");
                }
                chkkey(tmplist,"values[" + index + "]");

                StringBuilder buf0 = new StringBuilder(1024);
                for(Object key : tmplist){
                    wrap(buf0,",",DataUtil.getInstance().sqlValue(key));
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

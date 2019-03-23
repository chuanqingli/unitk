package unitk.plugin.mybatis.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import unitk.plugin.mybatis.provider.MyBatisProvider;

public interface MyBatisMapper{
    @SelectProvider(type = MyBatisProvider.class, method = "__call")
    @Options(statementType = StatementType.CALLABLE)
    public List<Map<String,Object>> call(Map<String,Object> map);

    @UpdateProvider(type = MyBatisProvider.class, method = "__update")
    public int update(Map<String,Object> map);

    @DeleteProvider(type = MyBatisProvider.class, method = "__delete")
    public int delete(Map<String,Object> map);

    @InsertProvider(type = MyBatisProvider.class, method = "__insert")
    public int insert(Map<String,Object> map);

    @InsertProvider(type = MyBatisProvider.class, method = "__insert")
    @Options(useGeneratedKeys = true, keyProperty = "_respkey")
    public int insertAndGetKey(Map<String,Object> map);

    @SelectProvider(type = MyBatisProvider.class, method = "__selectList")
    public List<Map<String,Object>> selectList(Map<String,Object> map);

    @SelectProvider(type = MyBatisProvider.class, method = "__selectCount")
    public int selectCount(Map<String,Object> map);

    @SelectProvider(type = MyBatisProvider.class, method = "__selectOne")
    public Map<String,Object> selectOne(Map<String,Object> map);

}

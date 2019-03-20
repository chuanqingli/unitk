package unitk.plugin.mybatis.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import unitk.plugin.mybatis.provider.MyBatisProvider;

public interface MyBatisMapper{
    @SelectProvider(type = MyBatisProvider.class, method = "__selectList")
    public List<Map<String,Object>> selectList(Map<String,Object> map);
}

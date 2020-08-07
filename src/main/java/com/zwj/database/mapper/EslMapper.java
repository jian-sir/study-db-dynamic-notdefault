package com.zwj.database.mapper;

import com.zwj.database.util.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author zhangweijian
 */
@Mapper
public interface EslMapper {

    @SelectProvider(method = "select1", type = SqlProvider.class)
    public List<Map<String, Object>> select1();

    @SelectProvider(method = "select2", type = SqlProvider.class)
    public List<Map<String, Object>> select2();

}

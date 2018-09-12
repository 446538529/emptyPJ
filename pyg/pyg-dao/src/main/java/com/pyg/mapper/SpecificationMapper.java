package com.pyg.mapper;

import com.pyg.pojo.TbSpecification;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<TbSpecification>{

    List<Map<String,String>> findSpecificationList();
}

package com.pyg.mapper;

import com.pyg.pojo.TbTypeTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TypeTemplateMapper extends Mapper<TbTypeTemplate> {
    List<Map<String,Object>> findOptionList();
}

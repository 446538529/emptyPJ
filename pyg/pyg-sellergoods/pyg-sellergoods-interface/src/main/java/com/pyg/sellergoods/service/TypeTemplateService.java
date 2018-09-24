package com.pyg.sellergoods.service;

import com.pyg.pojo.TbTypeTemplate;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService extends BaseService<TbTypeTemplate>{

    PageResult search(Integer page, Integer rows, TbTypeTemplate tbTypeTemplate);

    List<Map<String,Object>> findOptionList();

    List<Map> findSpecList(Long id);
}

package com.pyg.sellergoods.service;

import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbSpecification;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import com.pyg.vo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification>{

    PageResult search(Integer page, Integer rows, TbSpecification tbSpecification);

    void add(Specification specification);

    Specification findOne(Long id);

    void update(Specification specification);

    void deleteByIds(Long[] ids);

    List<Map<String,String>> findSpecificationList();
}

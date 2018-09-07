package com.pyg.sellergoods.service;

import com.pyg.pojo.TbBrand;
import com.pyg.servcie.BaseService;

import java.util.List;

public interface BrandService extends BaseService<TbBrand>{
    List<TbBrand> findAll();

    List<TbBrand> findPage(Integer page, Integer rows);
}

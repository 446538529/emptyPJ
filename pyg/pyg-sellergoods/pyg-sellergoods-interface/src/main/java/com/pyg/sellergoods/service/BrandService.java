package com.pyg.sellergoods.service;

import com.pyg.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    List<TbBrand> findAll();

    List<TbBrand> findPage(Integer page, Integer rows);
}

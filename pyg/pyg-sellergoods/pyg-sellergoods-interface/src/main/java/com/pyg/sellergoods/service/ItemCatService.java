package com.pyg.sellergoods.service;


import com.pyg.pojo.TbItemCat;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;

public interface ItemCatService extends BaseService<TbItemCat> {

    PageResult search(Integer page, Integer rows, TbItemCat itemCat);
}
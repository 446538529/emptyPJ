package com.pyg.sellergoods.service;

import com.pyg.pojo.TbSeller;
import com.pyg.vo.PageResult;
import com.pyg.servcie.BaseService;

public interface SellerService extends BaseService<TbSeller> {

    PageResult search(Integer page, Integer rows, TbSeller seller);
}
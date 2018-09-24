package com.pyg.sellergoods.service;

import com.pyg.pojo.TbGoods;
import com.pyg.servcie.BaseService;
import com.pyg.vo.Goods;
import com.pyg.vo.PageResult;


public interface GoodsService extends BaseService<TbGoods> {

    PageResult search(Integer page, Integer rows, TbGoods goods,String seller);

    PageResult search(Integer page, Integer rows, TbGoods goods);

    void addGood(Goods good);

    void addGoods(Goods good);

    Goods findGoods(Long id);

    void updateGoods(Goods goods);

    void updateStatus(Long[] ids, String status);

    void deleteGoods(Long[] ids);
}
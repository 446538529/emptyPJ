package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.SellerMapper;
import com.pyg.pojo.TbSeller;
import com.pyg.sellergoods.service.SellerService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = SellerService.class)
public class SellerServiceImpl extends BaseServiceImpl<TbSeller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbSeller seller) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbSeller.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(seller.getStatus())){
            criteria.andEqualTo("status",  seller.getStatus());
        }
        if(StringUtils.isNotBlank(seller.getName())){
            criteria.andLike("name",  "%"+seller.getName()+"%");
        }
        if(StringUtils.isNotBlank(seller.getNickName())){
            criteria.andLike("nickName",  "%"+seller.getNickName()+"%");
        }

        List<TbSeller> list = sellerMapper.selectByExample(example);
        PageInfo<TbSeller> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }
}

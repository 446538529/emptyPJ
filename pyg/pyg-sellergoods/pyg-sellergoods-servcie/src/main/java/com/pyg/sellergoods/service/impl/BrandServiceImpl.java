package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.sellergoods.service.BrandService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public List<TbBrand> findPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        return brandMapper.selectAll();
    }

    /**
     * 模糊查询并分页
     * @param page 当前页
     * @param rows 每页大小
     * @param tbBrand 查询条件
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, TbBrand tbBrand) {
        PageHelper.startPage(page,rows);
        Example example=new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(tbBrand.getName())){
            criteria.andLike("name","%"+tbBrand.getName()+"%");
        }
        if(StringUtils.isNotBlank(tbBrand.getFirstChar())){
            criteria.andEqualTo("firstChar",tbBrand.getFirstChar());
        }
        List<TbBrand> list = brandMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 返回品牌列表供模板页下拉使用
     * @return
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}

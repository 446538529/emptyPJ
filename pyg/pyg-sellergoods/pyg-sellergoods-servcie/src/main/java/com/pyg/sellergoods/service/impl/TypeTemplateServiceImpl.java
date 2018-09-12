package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.TypeTemplateMapper;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.sellergoods.service.TypeTemplateService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService{
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Override
    public PageResult search(Integer page, Integer rows, TbTypeTemplate tbTypeTemplate) {
        PageHelper.startPage(page,rows);
        Example example=new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(tbTypeTemplate.getName())){
            criteria.andLike("name","%"+tbTypeTemplate.getName()+"%");
        }
        List<TbTypeTemplate> list = typeTemplateMapper.selectByExample(example);
        PageInfo<TbTypeTemplate> pageInfo=new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
}

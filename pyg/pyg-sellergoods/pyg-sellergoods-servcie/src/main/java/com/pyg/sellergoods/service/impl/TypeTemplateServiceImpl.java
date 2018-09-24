package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.SpecificationMapper;
import com.pyg.mapper.SpecificationOptionMapper;
import com.pyg.mapper.TypeTemplateMapper;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.sellergoods.service.TypeTemplateService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
@Transactional
@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService{
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
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

    @Override
    public List<Map<String, Object>> findOptionList() {
        return typeTemplateMapper.findOptionList();
    }

    @Override
    public List<Map> findSpecList(Long id) {
        TbTypeTemplate typeTemplate = queryOne(id);
        List<Map> maps = JSONArray.parseArray(typeTemplate.getSpecIds(), Map.class);
        for (Map map : maps) {
            TbSpecificationOption specificationOption=new TbSpecificationOption();
            specificationOption.setSpecId(Long.parseLong(map.get("id").toString()));
            List<TbSpecificationOption> specificationOptions = specificationOptionMapper.select(specificationOption);
            map.put("options",specificationOptions);
        }
        return maps;
    }
}

package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.SpecificationMapper;
import com.pyg.mapper.SpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.sellergoods.service.SpecificationService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import com.pyg.vo.Specification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService{
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * 根据条件查询分页
     * @param page
     * @param rows
     * @param tbSpecification
     * @return 分页查询结果
     */
    public PageResult search(Integer page, Integer rows, TbSpecification tbSpecification) {
        PageHelper.startPage(page,rows);
        Example example=new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(tbSpecification.getSpecName())){
            criteria.andLike("specName","%"+tbSpecification.getSpecName()+"%");
        }
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo=new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 添加一个规格,包括规格选项
     * @param specification
     * @return
     */
    @Override
    public void add(Specification specification) {
        //先添加规格,并获取插入的id
        specificationMapper.insertSelective(specification.getSpecification());
        Long id = specification.getSpecification().getId();
        if(specification.getSpecificationOptionList()!=null&&specification.getSpecificationOptionList().size()>0){
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                //将规格id赋值后插入规格选项
                specificationOption.setSpecId(id);
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }

    /**
     * 根据id查询规格以及规格选项
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        //先查规格
        Specification specification=new Specification();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);
        //再根据id查规格选项
        TbSpecificationOption tbSpecificationOption=new TbSpecificationOption();
        tbSpecificationOption.setSpecId(id);
        List<TbSpecificationOption> specificationOptions = specificationOptionMapper.select(tbSpecificationOption);
        specification.setSpecificationOptionList(specificationOptions);
        return specification;
    }

    /**
     * 更新规格选项
     * @param specification
     */
    @Override
    public void update(Specification specification) {
        //先更新规格
        specificationMapper.updateByPrimaryKey(specification.getSpecification());
        //删除以前的所有规格选项
        TbSpecificationOption tbSpecificationOption=new TbSpecificationOption();
        tbSpecificationOption.setSpecId(specification.getSpecification().getId());
        specificationOptionMapper.delete(tbSpecificationOption);
        //添加页面传来的规格选项
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            specificationOption.setSpecId(specification.getSpecification().getId());
            specificationOptionMapper.insertSelective(specificationOption);
        }
    }

    /**
     * 根据id数组删除规格以及规格选项
     * @param ids
     */
    @Override
    public void deleteByIds(Long[] ids) {
        if(ids!=null&&ids.length>0){
            for (Long id : ids) {
                specificationMapper.deleteByPrimaryKey(id);
            }
            Example example=new Example(TbSpecificationOption.class);
            example.createCriteria().andIn("specId", Arrays.asList(ids));
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public List<Map<String, String>> findSpecificationList() {
        return specificationMapper.findSpecificationList();
    }
}

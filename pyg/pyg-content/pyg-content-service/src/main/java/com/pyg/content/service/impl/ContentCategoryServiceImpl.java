package com.pyg.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.content.service.ContentCategoryService;
import com.pyg.mapper.ContentCategoryMapper;
import com.pyg.pojo.TbContentCategory;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = ContentCategoryService.class)
public class ContentCategoryServiceImpl extends BaseServiceImpl<TbContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbContentCategory contentCategory) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbContentCategory.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(contentCategory.getName())){
            criteria.andLike("name", "%" + contentCategory.getName() + "%");
        }
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        PageInfo<TbContentCategory> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

}

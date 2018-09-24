package com.pyg.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.ContentMapper;
import com.pyg.pojo.TbContent;
import com.pyg.content.service.ContentService;
import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = ContentService.class)
public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String REDIS_CONTENT="content";
    @Override
    public PageResult search(Integer page, Integer rows, TbContent content) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbContent.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(!StringUtils.isEmpty(content.get***())){
            criteria.andLike("***", "%" + content.get***() + "%");
        }*/

        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<TbContent> findContentListByCategoryId(Long categoryId) {
        List<TbContent> list=null;

        try {
            list= (List<TbContent>) redisTemplate.boundHashOps(REDIS_CONTENT).get(categoryId);
            if(list!=null){
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Example example=new Example(TbContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        criteria.andEqualTo("status",1);
        example.orderBy("sortOrder").desc();
        list=contentMapper.selectByExample(example);
        try {
            redisTemplate.boundHashOps(REDIS_CONTENT).put(categoryId,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

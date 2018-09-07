package com.pyg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
    @Autowired
    private Mapper<T> mapper;
    @Override
    public T queryOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> queryByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public PageResult queryByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<T> list = mapper.selectAll();
        PageInfo<T> pageInfo=new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public PageResult queryByPage(Integer page, Integer rows, T t) {
        PageHelper.startPage(page,rows);
        List<T> list = mapper.select(t);
        PageInfo<T> pageInfo=new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteBatch(Serializable[] ids) {
        if(ids!=null&&ids.length>0){
            for (Serializable id : ids) {
                mapper.deleteByPrimaryKey(id);
            }
        }
    }
}

package com.pyg.servcie;

import com.pyg.vo.PageResult;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
    /**
     * 根据id查找
     * @param id id
     * @return 返回一个结果
     */
    T queryOne(Serializable id);

    /**
     * 查询所有列表
     * @return 返回所有结果的列表
     */
    List<T> queryAll();

    /**
     * 根据条件查询
     * @param t 查询条件,属性不为null就作为条件
     * @return
     */
    List<T> queryByWhere(T t);

    /**
     * 根据条件分页查询
     * @param page 当前页
     * @param rows 每页大小
     * @param t 查询条件
     * @return
     */
    PageResult queryByPage(Integer page,Integer rows,T t);

    /**
     * 无条件分页
     * @param page 当前页
     * @param rows 页面大小
     * @return
     */
    PageResult queryByPage(Integer page,Integer rows);

    /**
     * 新增一个
     * @param t 新增的对象
     */
    void add(T t);

    /**
     * 根据主键更新
     * @param t
     */
    void update(T t);

    /**
     * 根据id数组批量删除
     * @param ids 主键数组
     */
    void deleteBatch(Serializable[] ids);
}

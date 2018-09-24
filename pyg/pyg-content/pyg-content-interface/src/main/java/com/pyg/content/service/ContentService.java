package com.pyg.content.service;

import com.pyg.pojo.TbContent;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;

import java.util.List;

public interface ContentService extends BaseService<TbContent> {

    PageResult search(Integer page, Integer rows, TbContent content);

    List<TbContent> findContentListByCategoryId(Long categoryId);
}
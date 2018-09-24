package com.pyg.content.service;

import com.pyg.pojo.TbContentCategory;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;

public interface ContentCategoryService extends BaseService<TbContentCategory>{

    PageResult search(Integer page, Integer rows, TbContentCategory contentCategory);

}

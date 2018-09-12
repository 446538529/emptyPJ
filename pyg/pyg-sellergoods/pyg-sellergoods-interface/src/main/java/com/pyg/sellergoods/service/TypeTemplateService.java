package com.pyg.sellergoods.service;

import com.pyg.pojo.TbTypeTemplate;
import com.pyg.servcie.BaseService;
import com.pyg.vo.PageResult;

public interface TypeTemplateService extends BaseService<TbTypeTemplate>{

    PageResult search(Integer page, Integer rows, TbTypeTemplate tbTypeTemplate);

}

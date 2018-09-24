package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.content.service.ContentCategoryService;
import com.pyg.pojo.TbContentCategory;
import com.pyg.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contentCategory")
public class ContentCategoryController {
    @Reference
    private ContentCategoryService contentCategoryService;

    @PostMapping("search")
    public PageResult search(@RequestParam(name = "page",defaultValue = "1")Integer page,
                             @RequestParam(name = "rows",defaultValue = "5") Integer rows,
                             @RequestBody TbContentCategory contentCategory){
        return contentCategoryService.search(page,rows,contentCategory);
    }
    @GetMapping("queryAll")
    public List<TbContentCategory> queryAll(){
        return contentCategoryService.queryAll();
    }

}

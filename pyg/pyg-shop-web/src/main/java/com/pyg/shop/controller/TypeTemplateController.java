package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.sellergoods.service.TypeTemplateService;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("typeTemplate")
@RestController
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 根据条件分页查询模板
     * @param page
     * @param rows
     * @param tbTypeTemplate
     * @return
     */
    @PostMapping("search")
    public PageResult search(@RequestParam(name="page",defaultValue = "1") Integer page,
                             @RequestParam(name="rows",defaultValue = "10") Integer rows,
                             @RequestBody TbTypeTemplate tbTypeTemplate){
        return typeTemplateService.search(page,rows,tbTypeTemplate);
    }
    @PostMapping("add")
    public Result add(@RequestBody TbTypeTemplate tbTypeTemplate){
        try {
            typeTemplateService.add(tbTypeTemplate);
            return Result.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok("添加失败");
        }
    }
    @GetMapping("queryOne")
    public TbTypeTemplate findOne(Long id){
        return typeTemplateService.queryOne(id);
    }
    @PostMapping("update")
    public Result update(@RequestBody TbTypeTemplate tbTypeTemplate){
        try {
            typeTemplateService.update(tbTypeTemplate);
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok("更新失败");
        }
    }
    @GetMapping("delete")
    public Result delete(Long[] ids){
        try {
            typeTemplateService.deleteBatch(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }
    @GetMapping("findOptionList")
    public List<Map<String,Object>> findOptionList(){
        return typeTemplateService.findOptionList();
    }

    @GetMapping("findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }

}

package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbSpecification;
import com.pyg.sellergoods.service.BrandService;
import com.pyg.sellergoods.service.SpecificationService;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import com.pyg.vo.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;

    /**
     * 条件查询加分页
     * @param page
     * @param rows
     * @param tbSpecification
     * @return
     */
    @PostMapping("search")
    public PageResult search(@RequestParam(name="page",defaultValue = "1") Integer page,
                             @RequestParam(name="rows",defaultValue = "10") Integer rows,
                             @RequestBody TbSpecification tbSpecification){
        return specificationService.search(page,rows,tbSpecification);
    }

    /**
     * 新增规格
     * @param specification
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody Specification specification){
        try {
            specificationService.add(specification);
            return Result.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }
    }

    /**
     * 根据id查询规格
     * @param id
     * @return
     */
    @GetMapping("findOne")
    public Specification findOne(Long id){
        return specificationService.findOne(id);
    }
    @PostMapping("update")
    public Result update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新失败");
        }
    }
    @GetMapping("delete")
    public Result delete(Long[] ids){
        try {
            specificationService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok("删除失败");
        }
    }

    /**
     * 查询规格列表供模板页下拉使用
     * @return
     */
    @GetMapping("selectOptionList")
    public List<Map<String,String>> findSpecificationList(){
        return specificationService.findSpecificationList();
    }
}

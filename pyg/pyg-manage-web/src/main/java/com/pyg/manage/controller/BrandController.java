package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
        import com.pyg.pojo.TbBrand;
        import com.pyg.sellergoods.service.BrandService;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Reference(timeout =1200000)
    private BrandService brandService;
    @GetMapping("findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }
    @GetMapping("testPage")
    public List<TbBrand> testPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                                  @RequestParam(name = "rows",defaultValue = "5") Integer rows){
        return brandService.findPage(page,rows);
    }
    @GetMapping("queryAll")
    public List<TbBrand> queryAll(){
        return brandService.queryAll();
    }
    @GetMapping("queryPage")
    public List<TbBrand> queryPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                                  @RequestParam(name = "rows",defaultValue = "5") Integer rows){
        return (List<TbBrand>) brandService.queryByPage(page,rows).getRows();
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("findPage")
    public PageResult findPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                               @RequestParam(name = "rows",defaultValue = "5") Integer rows){
        return brandService.queryByPage(page,rows);
    }

    /**
     * 新增品牌
     * @param tbBrand
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody TbBrand tbBrand){
        try {
            brandService.add(tbBrand);
            return Result.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("添加失败");
    }

    /**
     * 更新品牌
     * @param tbBrand
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody TbBrand tbBrand){
        try {
            brandService.update(tbBrand);
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新失败");
    }
    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("findOne")
    public TbBrand findOne(Long id){
        return brandService.queryOne(id);
    }

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    @GetMapping("delete")
    public Result delete(Long[] ids){
        try {
            brandService.deleteBatch(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 查询并分页
     * @param page
     * @param rows
     * @param tbBrand
     * @return
     */
    @PostMapping("search")
    public PageResult search(@RequestParam(name = "page",defaultValue = "1")Integer page,
                             @RequestParam(name = "rows",defaultValue = "5") Integer rows,
                             @RequestBody TbBrand tbBrand){
        return brandService.search(page, rows, tbBrand);
    }
}

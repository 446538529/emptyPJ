package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbGoods;
import com.pyg.sellergoods.service.GoodsService;
import com.pyg.vo.Goods;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    @RequestMapping("/queryAll")
    public List<TbGoods> queryAll() {
        return goodsService.queryAll();
    }

    @GetMapping("/queryPage")
    public PageResult queryPage(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return goodsService.queryByPage(page, rows);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Goods good) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            //设置商家，设置审核状态未审核
            good.getGoods().setSellerId(name);
            good.getGoods().setAuditStatus("0");
            goodsService.addGoods(good);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("增加失败");
    }

    @GetMapping("/queryOne")
    public Goods queryOne(Long id) {
        return goodsService.findGoods(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        try {
            goodsService.updateGoods(goods);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.deleteGoods(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 分页查询列表
     * @param goods 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody  TbGoods goods, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return goodsService.search(page, rows, goods);
    }

    @GetMapping("updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            goodsService.updateStatus(ids,status);
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok("更新失败");
        }
    }
}

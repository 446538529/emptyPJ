package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbContent;
import com.pyg.content.service.ContentService;
import com.pyg.vo.PageResult;
import com.pyg.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/content")
@RestController
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/queryAll")
    public List<TbContent> queryAll() {
        return contentService.queryAll();
    }

    @GetMapping("/queryPage")
    public PageResult queryPage(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return contentService.queryByPage(page, rows);
    }

    @PostMapping("/add")
    public Result add(@RequestBody TbContent content) {
        try {
            contentService.add(content);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("增加失败");
    }

    @GetMapping("/queryOne")
    public TbContent queryOne(Long id) {
        return contentService.queryOne(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody TbContent content) {
        try {
            contentService.update(content);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            contentService.deleteBatch(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 分页查询列表
     * @param content 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody  TbContent content, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return contentService.search(page, rows, content);
    }

}

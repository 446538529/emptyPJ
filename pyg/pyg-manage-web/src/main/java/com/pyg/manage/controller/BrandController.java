package com.pyg.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
        import com.pyg.pojo.TbBrand;
        import com.pyg.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}

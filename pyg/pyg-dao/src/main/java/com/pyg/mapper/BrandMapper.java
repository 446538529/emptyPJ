package com.pyg.mapper;

import com.pyg.pojo.TbBrand;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<TbBrand>{
    List<TbBrand> findAll();
    /**
     * 返回品牌列表供模板页下拉使用
     * @return
     */
    List<Map<String,String>> selectOptionList();
}

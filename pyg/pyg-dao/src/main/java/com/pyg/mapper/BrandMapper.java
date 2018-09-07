package com.pyg.mapper;

        import com.pyg.pojo.TbBrand;
        import tk.mybatis.mapper.common.Mapper;

        import java.util.List;

public interface BrandMapper extends Mapper<TbBrand>{
    List<TbBrand> findAll();
}

package com.pyg.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.sellergoods.service.GoodsService;

import com.pyg.service.impl.BaseServiceImpl;
import com.pyg.vo.Goods;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = GoodsService.class)
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods,String seller) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sellerId",seller);
        criteria.andNotEqualTo("isDelete","1");
        if(!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }
        if(!StringUtils.isEmpty(goods.getAuditStatus())){
            criteria.andEqualTo("auditStatus",goods.getAuditStatus());
        }
        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }
        if(!StringUtils.isEmpty(goods.getAuditStatus())){
            criteria.andEqualTo("auditStatus",goods.getAuditStatus());
        }
        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 不包含sku列表
     * @param good
     */
    @Override
    public void addGood(Goods good) {
        goodsMapper.insertSelective(good.getGoods());
        good.getGoodsDesc().setGoodsId(good.getGoods().getId());
        goodsDescMapper.insertSelective(good.getGoodsDesc());
    }

    /**
     * 添加完整的goods信息
     * @param good
     */
    @Override
    public void addGoods(Goods good) {
        goodsMapper.insertSelective(good.getGoods());
        good.getGoodsDesc().setGoodsId(good.getGoods().getId());
        goodsDescMapper.insertSelective(good.getGoodsDesc());
        saveItemList(good);
    }

    private void saveItemList(Goods good){
        if("1".equals(good.getGoods().getIsEnableSpec())){
            for (TbItem item : good.getItemList()) {
                //拼接sku标题
                String title=good.getGoods().getGoodsName();
                Map<String ,Object> map=JSON.parseObject(item.getSpec());
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    title+=entry.getValue();
                }
                item.setTitle(title);
                setItemValue(good,item);
                itemMapper.insertSelective(item);
            }
        }else{
            TbItem tbItem = new TbItem();
            tbItem.setTitle(good.getGoods().getGoodsName());
            tbItem.setPrice(good.getGoods().getPrice());
            tbItem.setNum(9999);
            tbItem.setStatus("0");
            tbItem.setIsDefault("1");
            tbItem.setSpec("{}");
            setItemValue(good,tbItem);
            itemMapper.insertSelective(tbItem);
        }
    }
    private  void setItemValue(Goods good,TbItem item){
        //设置sku的图片
        List<Map> images = JSONArray.parseArray(good.getGoodsDesc().getItemImages(), Map.class);
        if(images!=null&&images.size()>0){
            item.setImage(images.get(0).get("url").toString());
        }
        //设置分类id
        item.setCategoryid(good.getGoods().getCategory3Id());
        //设置分类名称
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(good.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        //这种创建时间
        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());
        //设置spu的id
        item.setGoodsId(good.getGoods().getId());
        //设置商家id
        item.setSellerId(good.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(good.getGoods().getSellerId());
        item.setSeller(seller.getName());
        //设置品牌
        TbBrand brand = brandMapper.selectByPrimaryKey(good.getGoods().getBrandId());
        item.setBrand(brand.getName());
    }

    @Override
    public Goods findGoods(Long id) {
        Goods goods=new Goods();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);
        //查询sku
        Example example=new Example(TbItem.class);
        example.createCriteria().andEqualTo("goodsId",id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        goods.setItemList(tbItems);
        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {
        goods.getGoods().setAuditStatus("0");
        goodsMapper.updateByPrimaryKeySelective(goods.getGoods());
        goodsDescMapper.updateByPrimaryKeySelective(goods.getGoodsDesc());
        TbItem item=new TbItem();
        item.setGoodsId(goods.getGoods().getId());
        itemMapper.delete(item);
        saveItemList(goods);
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TbGoods goods=new TbGoods();
        goods.setAuditStatus(status);
        Example example=new Example(TbGoods.class);
        example.createCriteria().andIn("id", Arrays.asList(ids));
        goodsMapper.updateByExampleSelective(goods,example);

    }

    @Override
    public void deleteGoods(Long[] ids) {
        TbGoods goods=new TbGoods();
        goods.setIsDelete("1");
        Example example=new Example(TbGoods.class);
        example.createCriteria().andIn("id",Arrays.asList(ids));
        goodsMapper.updateByExampleSelective(goods,example);
    }
}

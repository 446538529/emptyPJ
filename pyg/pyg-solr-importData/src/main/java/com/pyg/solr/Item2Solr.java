package com.pyg.solr;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.ItemMapper;
import com.pyg.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class Item2Solr {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private ItemMapper itemMapper;
    @Test
    public void test(){
        TbItem item=new TbItem();
        item.setStatus("1");
        List<TbItem> items = itemMapper.select(item);
        for (TbItem tbItem : items) {
            Map map = JSON.parseObject(tbItem.getSpec(), Map.class);
            tbItem.setSpecMap(map);
        }
        solrTemplate.saveBeans(items);
        solrTemplate.commit();
    }
}

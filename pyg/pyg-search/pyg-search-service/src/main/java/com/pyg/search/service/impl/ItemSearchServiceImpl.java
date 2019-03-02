package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(interfaceClass = ItemSearchService.class)
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String,Object> map=new HashMap<>();
        /*SimpleQuery query=new SimpleQuery();*/
        SimpleHighlightQuery query=new SimpleHighlightQuery();
        Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //设置高亮
        HighlightOptions highlightOptions=new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red;weight:bold'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);
        
       /* ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);*/
       //处理高亮域
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);
        List<HighlightEntry<TbItem>> highlightEntries = highlightPage.getHighlighted();
        if(highlightEntries!=null&&highlightEntries.size()>0){
            for (HighlightEntry<TbItem> highlightEntry : highlightEntries) {
                List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
                if(highlights!=null&&highlights.size()>0&&highlights.get(0).getSnipplets()!=null){
                    highlightEntry.getEntity().setTitle(highlights.get(0).getSnipplets().get(0));
                }
            }
        }
        map.put("rows",highlightPage.getContent());
        return map;
    }

}

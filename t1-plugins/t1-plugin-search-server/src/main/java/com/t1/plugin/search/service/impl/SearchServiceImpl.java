package com.t1.plugin.search.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.t1.common.model.PageResult;
import com.t1.common.search.model.SearchDto;
import com.t1.es.utils.SearchBuilder;
import com.t1.plugin.search.service.ISearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 通用搜索
 *
 * @author Bruce Lee (copy)
 * @date 2019/4/24
 */
@Service
public class SearchServiceImpl implements ISearchService {
    private final RestHighLevelClient client;

    public SearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException {
        return SearchBuilder.builder(client, indexName)
                .setStringQuery(searchDto.getQueryStr())
                .addSort(searchDto.getSortCol(), SortOrder.DESC)
                .setIsHighlight(searchDto.getIsHighlighter())
                .getPage(searchDto.getPage(), searchDto.getLimit());
    }
}

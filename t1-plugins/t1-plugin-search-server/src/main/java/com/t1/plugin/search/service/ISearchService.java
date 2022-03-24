package com.t1.plugin.search.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.t1.common.model.PageResult;
import com.t1.common.search.model.SearchDto;

import java.io.IOException;

/**
 * @author Bruce Lee (copy)
 * @date 2019/4/24
 */
public interface ISearchService {
    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException;
}

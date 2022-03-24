package com.t1.plugin.search.admin.controller;

import com.t1.common.model.PageResult;
import com.t1.common.model.R;
import com.t1.plugin.search.admin.model.IndexDto;
import com.t1.plugin.search.admin.properties.IndexProperties;
import com.t1.plugin.search.admin.service.IIndexService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * 索引管理
 *
 * @author Bruce Lee (copy)
 */
@Slf4j
@RestController
@Api(tags = "索引管理api")
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private IIndexService indexService;

    @Autowired
    private IndexProperties indexProperties;

    @PostMapping("/index")
    public R createIndex(@RequestBody IndexDto indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        return R.success("操作成功");
    }

    /**
     * 索引列表
     */
    @GetMapping("/indices")
    public PageResult<Map<String, String>> list(@RequestParam(required = false) String queryStr) throws IOException {
        return indexService.list(queryStr, indexProperties.getShow());
    }

    /**
     * 索引明细
     */
    @GetMapping("/index")
    public R<Map<String, Object>> showIndex(String indexName) throws IOException {
        Map<String, Object> result = indexService.show(indexName);
        return R.success(result);
    }

    /**
     * 删除索引
     */
    @DeleteMapping("/index")
    public R deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return R.success("操作成功");
    }
}

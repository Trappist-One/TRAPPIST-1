package com.t1.biz.activiti.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * JSON模型
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/2/18
 */
@Data
@Accessors(chain = true)
public class JsonMode {

   /**
    * 目前表单内所有的控件数据
    */
   private List<Map<String,Object>> drawingControls;

   /**
    * formConf表单配置
    */
   private Map<String,Object> formConf;

}

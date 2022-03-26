package com.t1.sys.activiti.diagramgenerate.processPicture;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 自定义图表生成器接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/15
 */
public interface CustomProcessDiagramGeneratorService extends ProcessDiagramGenerator {

    InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName,
                                ClassLoader customClassLoader, double scaleFactor, Color[] colors, Set<String> currIds);

}

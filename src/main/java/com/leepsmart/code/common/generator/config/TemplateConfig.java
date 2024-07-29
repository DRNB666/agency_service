package com.leepsmart.code.common.generator.config;

import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import java.util.Map;

/**
 * @author leepsmart
 */
public class TemplateConfig extends AbstractTemplateEngine {

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) {

    }


    @Override
    public String templateFilePath(String filePath) {
        System.out.println(filePath);
        return null;
    }

}

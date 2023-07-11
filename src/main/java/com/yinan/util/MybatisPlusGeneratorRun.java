/**
 * @(#)MybatisPlusGeneratorRun.java, 2021/11/16.
 * <p/>
 */
package com.yinan.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author 吴朝鑫 (wuchaoxin@corp.netease.com)
 */
public class MybatisPlusGeneratorRun {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(
            "jdbc:mysql://localhost:3306/personal", "root", "zyn123zyn");

    /**
     * 执行 run
     */
    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG).globalConfig(builder -> {
            // 设置作者
            builder.author("Yinan Zhang (zhangyinan01@corp.netease.com)")
                    // 覆盖已生成文件
                    .fileOverride().outputDir("/Users/yinan/Downloads");
            // 指定输出目录
        }).packageConfig(builder -> {
            // 设置父包名
            builder.parent("com.tob.mall.tob.cart")
                    // 设置父包模块名
                    .moduleName("dal")
                    // 设置mapperXml生成路径
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/yinan/Downloads"));
        }).strategyConfig(builder -> {
            // 设置需要生成的表名
            builder.addInclude("TB_TOB_MALL_CART_ITEM").entityBuilder().enableTableFieldAnnotation().enableLombok();
            // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        }).templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}

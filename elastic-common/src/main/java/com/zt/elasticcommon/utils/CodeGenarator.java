package com.zt.elasticcommon.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @Description
 * @Author kzh
 * @Date 2019/7/2 11:39
 */
public class CodeGenarator {

    public static void main(String[] args) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setAuthor("MR.zt")
                .setOutputDir("D:\\data")
                .setFileOverride(true)
                .setIdType(IdType.ID_WORKER)
                .setServiceName("%sService")
                .setMapperName("%sDao")
                .setActiveRecord(true)
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://172.16.10.99:3306/neti?serverTimezone=GMT%2B8")
                .setUsername("root")
                .setPassword("123456");

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setInclude("t_job_status_trace_log","t_job_execution_log")
                .setCapitalMode(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setEntityBuilderModel(true)
                .setRestControllerStyle(true)
                .entityTableFieldAnnotationEnable(true)
        ;

        PackageConfig packageConfig = new PackageConfig();
        packageConfig
                .setParent("com.zt.elasticcommon")
                .setMapper("dao")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("mapper");

        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();



    }


}

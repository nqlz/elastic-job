package com.zt.elasticcommon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.zt.elasticcommon.dao")
public class ElasticCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticCommonApplication.class, args);
    }

}

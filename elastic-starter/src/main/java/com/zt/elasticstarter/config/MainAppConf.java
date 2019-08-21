package com.zt.elasticstarter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/20 10:49
 */
@Configuration
@MapperScan("com.zt.elasticcommon.dao")
@ComponentScan("com.zt.elasticcommon")
public class MainAppConf {
}

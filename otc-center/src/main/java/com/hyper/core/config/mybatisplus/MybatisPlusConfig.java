package com.hyper.core.config.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

/** 
 * @ClassName  MybatisPlusConfig
 * @author  泡面和尚
 * @date  2017年12月21日 下午3:32:16
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"com.baomidou.cloud.service.*.mapper*", "com.hyper.**.mapper.*Mapper"})
public class MybatisPlusConfig {
	
	/**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
	
}

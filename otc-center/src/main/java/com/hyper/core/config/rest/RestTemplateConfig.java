package com.hyper.core.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/** 
 * RestTemplate配置
 * @ClassName  RestTemplateConfig 
 * @date  2018年1月10日 下午7:47:12 
 */
@Configuration
public class RestTemplateConfig {
    
    @Bean  
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory){  
        return new RestTemplate(httpClientFactory);  
    }
    
    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory(){  
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();  
        requestFactory.setReadTimeout(3500);
        requestFactory.setConnectTimeout(5000);
        return requestFactory;
    } 

}

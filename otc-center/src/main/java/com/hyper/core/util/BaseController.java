package com.hyper.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * 
 * @ClassName  BaseController 
 * @date  2017年7月19日 上午9:10:47
 */
public abstract class BaseController {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected static String AdminRequestPrefix = "/admin";
    
    protected static String AdminPagePrefix = "admin/";

    protected static String AdminPrefix = "admin/";
    
    protected static String AccessPrefix = "admin";
    
    /**
     * 查询参数转like
     * @Title  queryToLike
     * @author  泡面和尚
     * @param ew
     * @param column
     * @param value
     * @return  void
     */
    protected void queryToLike(EntityWrapper<?> ew, String column, String value){
    	if(!StringUtils.isEmpty(value)){
    		ew.like(column, value);
    	}
    }
    
    /**
     * 查询参数转eq
     * @Title  queryToEq
     * @author  泡面和尚
     * @param ew
     * @param column
     * @param value
     * @return  void
     */
    protected void queryToEq(EntityWrapper<?> ew, String column, Object value){
    	if(value != null){
    		ew.eq(column, value);
    	}
    }
    
}

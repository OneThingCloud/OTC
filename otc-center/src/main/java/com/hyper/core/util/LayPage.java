package com.hyper.core.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;

/** 
 * MyBatisplusPage - LayPage 转化工具
 * @ClassName  LayPage
 * @author  泡面和尚
 * @date  2017年12月21日 下午2:09:10
 */
public class LayPage<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据
	 */
	private List<T> data = Collections.emptyList();
	
	/**
	 * 总数
	 */
	private int count;
	
	/**
	 * 状态 默认0 成功 -1 失败 
	 */
	private int code = 0;
	
	/**
	 * 状态信息
	 */
	private String msg = "";
	
	public LayPage(Page<T> page){
		this.data = page.getRecords();
		this.count = page.getTotal();
	}
	
	public LayPage(List<T> list){
		this.data = list;
		this.count = list.size();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

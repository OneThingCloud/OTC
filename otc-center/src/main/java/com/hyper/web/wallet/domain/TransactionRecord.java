/*
    GPL(GNU Public Library) is a Library Management System.
    Copyright (C) 2018-2020  Huang Jie
    This file is part of GPL.
    GPL is a free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.hyper.web.wallet.domain;

import java.io.Serializable;

/** 
 * @ClassName  TransactionRecord
 * @author  泡面和尚
 * @date  2018年1月24日 下午4:33:53
 */
public class TransactionRecord implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	private String timestamp;
	private int type;
	private String tradeAccount;
	private String amount;
	private String cost;
	private String hash;
	private String title;
	private String extra;
	private String order_id;
	
	public String getTimestamp() {
		return timestamp;
	}
	public int getType() {
		return type;
	}
	public String getTradeAccount() {
		return tradeAccount;
	}
	public String getAmount() {
		return amount;
	}
	public String getCost() {
		return cost;
	}
	public String getHash() {
		return hash;
	}
	public String getTitle() {
		return title;
	}
	public String getExtra() {
		return extra;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}

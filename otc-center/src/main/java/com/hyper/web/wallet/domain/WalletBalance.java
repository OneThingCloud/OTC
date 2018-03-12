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
 * @ClassName  WalletBalance
 * @author  泡面和尚
 * @date  2018年2月4日 下午9:54:41
 */
public class WalletBalance implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	private String jsonrpc;
	private int id;
	private String result;
	public String getJsonrpc() {
		return jsonrpc;
	}
	public int getId() {
		return id;
	}
	public String getResult() {
		return result;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}

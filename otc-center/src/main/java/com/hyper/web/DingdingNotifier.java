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
package com.hyper.web;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** 
 * @ClassName  DingdingNotifier
 * @author  泡面和尚
 * @date  2018年1月6日 下午10:34:54
 */
@Component
public class DingdingNotifier {
	
	private static final String DINGDING_ROBOT_SERVER_URL = "https://oapi.dingtalk.com/robot/send?access_token=";
	
	@Value("${dingding.token}")
	private String accessToken;
	
	private static HttpHeaders httpHeaders;
	private RestTemplate restTemplate = new RestTemplate();
	
	private static final String TEXT_MESSAGE = "{\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
	private static final String MARKDOWN_MESSAGE = "{\"msgtype\":\"markdown\",\"markdown\":{\"title\":\"%s\",\"text\":\"%s\"}}";
	
	public DingdingNotifier() {
	    httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}
	
	public void doNotify(String title, String message){
		HttpEntity<String> httpEntity = new HttpEntity<String>(String.format(MARKDOWN_MESSAGE, StringEscapeUtils.escapeJava(title),
				StringEscapeUtils.escapeJava(message)), httpHeaders);
		restTemplate.postForEntity(DINGDING_ROBOT_SERVER_URL + accessToken, httpEntity, Void.class);
	}
	 
	
}

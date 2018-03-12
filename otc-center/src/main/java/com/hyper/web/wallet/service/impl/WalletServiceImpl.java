package com.hyper.web.wallet.service.impl;

import com.hyper.web.wallet.domain.Wallet;
import com.hyper.web.wallet.domain.WalletBalance;
import com.hyper.web.wallet.domain.WalletapiResult;
import com.hyper.web.wallet.mapper.WalletMapper;
import com.hyper.web.wallet.service.IWalletService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 链克钱包 服务实现类
 * </p>
 *
 * @author 泡面和尚123
 * @since 2018-01-24
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements IWalletService {
	
	private Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	private final static String remoteBaseRecordApi = "https://walletapi.onethingpcs.com";
	private final static String remoteTransactionRecordApi = "https://walletapi.onethingpcs.com/getTransactionRecords";
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) OTCWallet/1.3.0 Chrome/58.0.3029.110 Electron/1.7.9 Safari/537.36";
	private final static String data = "[\"%s\",\"0\",\"0\",\"%s\",\"%s\"]";
	private final static String balanceData = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"eth_getBalance\", \"params\": [\"%s\",\"delay\"]}";
	private static HttpHeaders headers = null;
	
	static{
		headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setConnection("close");
		headers.set("USER-AGENT", USER_AGENT);
		headers.setCacheControl("no-cache");
	}
	
	@Override
	public WalletapiResult queryByAccount(String account) {
		WalletapiResult result = remoteTransactionRecords(account, 1, 10);
		return result;
	}
	
	@Override
	public WalletapiResult remoteTransactionRecords(String account, int page, int pageSize){
		try {
		    HttpEntity<String> entity = new HttpEntity<>(String.format(data, account, page+"", pageSize+""), headers);
		    String url = remoteTransactionRecordApi + "?t=" + System.currentTimeMillis();
		    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
		    if(responseEntity.getStatusCode().value() == 200){
		    	String result = responseEntity.getBody();
		    	return JSON.parseObject(result, WalletapiResult.class);
		    }
		} catch (Exception e) {
			logger.error("api query error：{}, {}", account, e.getMessage());
		    e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryBalance(String account) {
		try {
		    HttpEntity<String> entity = new HttpEntity<>(String.format(balanceData, account), headers);
		    String url = remoteBaseRecordApi + "?t=" + System.currentTimeMillis();
		    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
		    if(responseEntity.getStatusCode().value() == 200){
		    	String result = responseEntity.getBody();
		    	return JSON.parseObject(result, WalletBalance.class).getResult();
		    }
		} catch (Exception e) {
			logger.error("api balance error：{}, {}", account, e.getMessage());
		    e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateOrlWallet() {
		return baseMapper.updateOrlWallet();
	}

}

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
package com.hyper.web.wallet.job;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hyper.web.DingdingNotifier;
import com.hyper.web.wallet.domain.TransactionRecord;
import com.hyper.web.wallet.domain.Wallet;
import com.hyper.web.wallet.domain.WalletapiResult;
import com.hyper.web.wallet.service.IWalletService;

/** 
 * @ClassName  WalletJob
 * @author  泡面和尚
 * @date  2018年1月24日 下午4:58:41
 */
@Component
@EnableAsync
public class WalletJob {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int PAGESIZE = 40;
	
	@Value("${nums_thread:5}")
	public int nThreads;
	@Value("${api_trans_page_size:20}")
	public int transPageSize;
	
	@Autowired
	private IWalletService walletService;
	@Autowired
	private DingdingNotifier dingdingNotifier;
	
	private ExecutorService executorService = null;
	
	private static Set<String> accounts = new HashSet<>();
	
	//@PostConstruct
	private void initThreadPool(){
		executorService = Executors.newFixedThreadPool(nThreads);
	}
	
	
	@Async
	@Scheduled(fixedDelay=1000*60*60)
	public void toNotify(){
		int count = walletService.selectCount(null);
		StringBuffer sb = new StringBuffer();
		sb.append("### [链克统计](http://red.xunlei.com/index.php?r=site/coin) \n");
		sb.append("> ###### 已扫描到：" + count + " \n");
		sb.append("> ###### "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+" \n");
		dingdingNotifier.doNotify("链克区块统计", sb.toString());
	}
	
	
	@Scheduled(fixedDelay=1000*30)
	public void initAsync(){
		executorService = Executors.newFixedThreadPool(nThreads);
		//清理历史
		walletService.updateOrlWallet();
		EntityWrapper<Wallet> wrapper = new EntityWrapper<>();
		wrapper.orderBy("updatetime", true);
		List<Wallet> list = walletService.selectList(wrapper);
		List<Wallet> newWallets = new ArrayList<>();
		list.stream().forEach(wallet -> {
			accounts.add(wallet.getAccount());
			if(wallet.getStatus() != null && wallet.getStatus() == 1){
				newWallets.add(wallet);
			}
		});
		logger.info("wallet start, all:{}, use:{}", list.size(), newWallets.size());
		getAsyncDealResult(newWallets);
	}
	
	private void getAsyncDealResult(List<Wallet> list){
		int size = list.size();
		int taskcount = (int) Math.ceil(size / (float) nThreads);
		int listStart, listEnd;
		for (int i = 0; i < nThreads; i++) {
		    listStart = taskcount * i;
		    listEnd = taskcount * ( i + 1 );
		    if (i == nThreads - 1) {
		    	listEnd = size;
		    }
		    final List<Wallet> subList = list.subList(listStart, listEnd);  
		    Runnable runnable = new Runnable() {
				@Override
				public void run() {
					remoteTradeRecords(subList);
				}
			};
			executorService.submit(runnable);
			if(size <= nThreads){
				break;
			}
		}
		executorService.shutdown();
		try {
			 while(!executorService.awaitTermination(1, TimeUnit.SECONDS)){
				 Thread.sleep(5000);
			 }
			 int old = walletService.updateOrlWallet();
			 StringBuffer sb = new StringBuffer();
			 sb.append("### [链克统计](http://red.xunlei.com/index.php?r=site/coin) \n");
			 sb.append("> ###### 本周期完成：" + list.size() + " \n");
			 sb.append("> ###### 旧钱包清理：" + old + " \n");
			 sb.append("> ###### "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+" \n");
			 dingdingNotifier.doNotify("链克区块统计", sb.toString());
			 logger.info("Deal done ! ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void remoteTradeRecords(List<Wallet> list){
		for(Wallet wallet:list){
			int page = 1;
			int pageSize = wallet.getLasttranstime() == null ? transPageSize:20;
			boolean next = true;
			while(next){
				next = parseTrade(wallet, page, pageSize);
				page ++;
			}
			logger.info("update trade records done:{}, {}, {}", wallet.getAccount(), wallet.getMoney(), wallet.getTotalnum());
		}
	}
	
	private boolean parseTrade(Wallet wallet, int page, int pageSize){
		try {
			WalletapiResult result = walletService.remoteTransactionRecords(wallet.getAccount(), page, pageSize);
			if(result == null || CollectionUtils.isEmpty(result.getResult())){
				return false;
			}
			//最后一次交易
			if(page == 1){
				String balance = walletService.queryBalance(wallet.getAccount());
				if(balance != null){
					wallet.setMoney(hex2Double(balance));
				}
				wallet.setTotalnum(result.getTotalnum());
				TransactionRecord lasttranst = result.getResult().get(0);
				wallet.setLasttranstamount(hex2Double(lasttranst.getAmount()));
				wallet.setLasttranstime(time2Date(lasttranst.getTimestamp()));
				wallet.setLasttype(lasttranst.getType());
				wallet.setUpdatetime(new Date());
				walletService.updateById(wallet);
			}
			//交易
			for(TransactionRecord record:result.getResult()){
				if(wallet.getLasttranstime() != null && wallet.getLasttranstime().equals(time2Date(record.getTimestamp()))){
					break;
				}
				if(!accounts.contains(record.getTradeAccount())){
					//保存新的账号记录
					saveNewAccount(record);
				}
			}
			return result.getTotalnum() > PAGESIZE*page;
		} catch (Exception e) {
			logger.error("save account:{}", e);
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveNewAccount(TransactionRecord record){
		try {
			Wallet trans = new Wallet();
			trans.setAccount(record.getTradeAccount());
			trans.setLasttranstamount(hex2Double(record.getAmount()));
			trans.setLasttranstime(time2Date(record.getTimestamp()));
			trans.setLasttype(record.getType());
			trans.setUpdatetime(new Date());
			trans.setCreatetime(new Date());
			walletService.insert(trans);
			//记录
			accounts.add(trans.getAccount());
		} catch (Exception e) {
			logger.error("parseTrade:{},{}", record.getTradeAccount(), e);
			e.printStackTrace();
		}
	}
	
	private static Date time2Date(String timestamp){
		return new Date(new Long(timestamp)*1000);
	}
	
	private static double hex2Double(String hex){
		BigInteger bigInteger = new BigInteger(hex.substring(2), 16);
		return bigInteger.doubleValue()/Math.pow(10, 18);
	}
	
}

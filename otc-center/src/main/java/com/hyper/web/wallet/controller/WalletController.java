package com.hyper.web.wallet.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.hyper.core.util.BaseController;
import com.hyper.web.wallet.domain.WalletapiResult;
import com.hyper.web.wallet.service.IWalletService;

/**
 * <p>
 * 链克钱包 前端控制器
 * </p>
 *
 * @author 泡面和尚123
 * @since 2018-01-24
 */
@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController {
	
	@Autowired
	private IWalletService walletService;
	
	@RequestMapping("/serach")
	@ResponseBody
	public WalletapiResult serach(@RequestParam("account")String account){
		WalletapiResult result = walletService.queryByAccount(account);
		return result;
	}

}


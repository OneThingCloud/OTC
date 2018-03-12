package com.hyper.web.wallet.service;

import com.hyper.web.wallet.domain.Wallet;
import com.hyper.web.wallet.domain.WalletapiResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 链克钱包 服务类
 * </p>
 *
 * @author 泡面和尚123
 * @since 2018-01-24
 */
public interface IWalletService extends IService<Wallet> {

	WalletapiResult queryByAccount(String account);

	WalletapiResult remoteTransactionRecords(String string, int page, int pageSize);

	String queryBalance(String account);

	int updateOrlWallet();

}

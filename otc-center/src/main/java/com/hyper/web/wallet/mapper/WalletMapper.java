package com.hyper.web.wallet.mapper;

import com.hyper.web.wallet.domain.Wallet;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 链克钱包 Mapper 接口
 * </p>
 *
 * @author 泡面和尚123
 * @since 2018-01-24
 */
public interface WalletMapper extends BaseMapper<Wallet> {

	int updateOrlWallet();

}

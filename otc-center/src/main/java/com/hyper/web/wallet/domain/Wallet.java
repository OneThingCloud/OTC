package com.hyper.web.wallet.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 链克钱包
 * </p>
 *
 * @author 泡面和尚123
 * @since 2018-01-24
 */
@TableName("t_wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @TableId(value = "account", type = IdType.INPUT)
    private String account;
    /**
     * 账号余额
     */
    private Double money;
    /**
     * 交易数
     */
    private Integer totalnum;
    /**
     * 最后一次交易金额
     */
    private Double lasttranstamount;
    /**
     * 最后一次交易类型(0:转出 1:转入)
     */
    private Integer lasttype;
    /**
     * 上次交易时间
     */
    private Date lasttranstime;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;
    
    /**
     * 状态
     */
    private Integer status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Date getLasttranstime() {
        return lasttranstime;
    }

    public void setLasttranstime(Date lasttranstime) {
        this.lasttranstime = lasttranstime;
    }

	public Double getLasttranstamount() {
		return lasttranstamount;
	}

	public Integer getLasttype() {
		return lasttype;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setLasttranstamount(Double lasttranstamount) {
		this.lasttranstamount = lasttranstamount;
	}

	public void setLasttype(Integer lasttype) {
		this.lasttype = lasttype;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Double getMoney() {
		return money;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

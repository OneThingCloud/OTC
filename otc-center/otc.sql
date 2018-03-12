/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.244_3306
Source Server Version : 50716
Source Host           : 192.168.1.244:3306
Source Database       : otc

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-03-12 16:19:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet`;
CREATE TABLE `t_wallet` (
  `account` varchar(50) NOT NULL COMMENT '账号',
  `money` double(20,8) DEFAULT '0.00000000' COMMENT '账号余额',
  `totalnum` int(11) DEFAULT '0' COMMENT '交易数',
  `lasttranstamount` double(20,8) DEFAULT NULL,
  `lasttype` int(11) DEFAULT NULL COMMENT '最后一次交易类型(0:转出 1:转入)',
  `lasttranstime` timestamp NULL DEFAULT NULL COMMENT '上次交易时间',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1' COMMENT '账号状态(1:开启 0:关闭)',
  PRIMARY KEY (`account`),
  KEY `idx_account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='链克钱包';

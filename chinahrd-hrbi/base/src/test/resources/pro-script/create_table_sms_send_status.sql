

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sms_send_status`
-- ----------------------------
DROP TABLE IF EXISTS `sms_send_status`;
CREATE TABLE `sms_send_status` (
  `Id` varchar(32) NOT NULL COMMENT 'ID',
  `provider` varchar(20) DEFAULT NULL COMMENT '短信接口提供商',
  `msgNo` varchar(20) NOT NULL COMMENT '发送短信编号',
  `mobile` varchar(18) NOT NULL COMMENT '电话号码',
  `smsTxt` varchar(500) DEFAULT NULL COMMENT '短信内容',
  `sendTime` time DEFAULT NULL COMMENT '发送时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '发送状态，0-未成功 1-成功',
  `telecomStatus` varchar(10) DEFAULT NULL COMMENT '运营商返回状态',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
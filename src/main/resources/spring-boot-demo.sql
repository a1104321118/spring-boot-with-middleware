

CREATE DATABASE `spring-boot-demo`;
use `spring-boot-demo`;
-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '100000000000001', '测试', '12345679800', '2017-12-29 14:32:50');
INSERT INTO `account` VALUES ('2', '100000000000001', '测试', '12345679800', '2017-12-29 14:33:11');
INSERT INTO `account` VALUES ('3', '100000000000001', '测试', '12345679800', '2017-12-29 14:33:11');
INSERT INTO `account` VALUES ('4', '100000000000001', '测试', '12345679800', '2017-12-29 14:33:11');
INSERT INTO `account` VALUES ('5', '100000000000001', '测试', '12345679800', '2017-12-29 14:33:12');

-- ----------------------------
-- Table structure for id_test
-- ----------------------------
DROP TABLE IF EXISTS `id_test`;
CREATE TABLE `id_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(32) DEFAULT NULL,
  `current_no` bigint(20) DEFAULT NULL,
  `step_size` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of id_test
-- ----------------------------
INSERT INTO `id_test` VALUES ('1', 'account', '1600', '200', '2017-12-29 15:26:24');
INSERT INTO `id_test` VALUES ('2', 'trade', '4000', '500', '2017-12-29 15:26:24');

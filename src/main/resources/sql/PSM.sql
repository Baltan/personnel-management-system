/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : PSM

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 07/07/2017 15:36:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) NOT NULL,
  `department` varchar(20) NOT NULL,
  `salary` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
BEGIN;
INSERT INTO `person` VALUES (1001, 'songjiang', 'male', 'Human Resource', 6000);
INSERT INTO `person` VALUES (1002, 'lujunyi', 'male', 'Market', 6500);
INSERT INTO `person` VALUES (1003, 'wuyong', 'female', 'Sales', 6500);
INSERT INTO `person` VALUES (1004, 'gongsunsheng', 'male', 'Market', 7000);
INSERT INTO `person` VALUES (1005, 'guansheng', 'male', 'Sales', 7500);
INSERT INTO `person` VALUES (1006, 'linchong', 'female', 'Accounting', 5500);
INSERT INTO `person` VALUES (1007, 'qinming', 'female', 'Accounting', 5800);
INSERT INTO `person` VALUES (1008, 'huyanzhuo', 'male', 'Salse', 8000);
INSERT INTO `person` VALUES (1009, 'huarong', 'female', 'Sales', 5000);
INSERT INTO `person` VALUES (1010, 'chaijin', 'female', 'Market', 5700);
INSERT INTO `person` VALUES (1011, 'liying', 'male', 'Sales', 5200);
INSERT INTO `person` VALUES (1012, 'zhutong', 'female', 'Sales', 6300);
INSERT INTO `person` VALUES (1013, 'luzhishen', 'male', 'Market', 6700);
INSERT INTO `person` VALUES (1014, 'wusong', 'male', 'Market', 6800);
INSERT INTO `person` VALUES (1015, 'dongping', 'female', 'Human Resource', 5400);
INSERT INTO `person` VALUES (1016, 'zhangqing', 'female', 'Accounting', 6000);
INSERT INTO `person` VALUES (1017, 'yangzhi', 'male', 'Human Resource', 5400);
INSERT INTO `person` VALUES (1018, 'xuning', 'female', 'Accounting', 5300);
INSERT INTO `person` VALUES (1019, 'lijun', 'male', 'Market', 6000);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', '1');
INSERT INTO `user` VALUES ('admin', '123456');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

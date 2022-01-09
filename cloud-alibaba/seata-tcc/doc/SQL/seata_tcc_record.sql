/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : localhost:3306
 Source Schema         : seata_tcc_record

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 09/01/2022 17:44:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for transactional_record
-- ----------------------------
DROP TABLE IF EXISTS `transactional_record`;
CREATE TABLE `transactional_record`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NULL DEFAULT NULL COMMENT '1. try  2 commit 3 cancel ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

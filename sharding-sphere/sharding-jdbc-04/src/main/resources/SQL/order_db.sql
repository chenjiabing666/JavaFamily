/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : localhost:3306
 Source Schema         : order_db

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 21/05/2022 22:17:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`  (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `price` decimal(10, 2) NOT NULL COMMENT '订单价格',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户id',
  `status` int(1) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_order_1
-- ----------------------------
INSERT INTO `t_order_1` VALUES (7348789673029795864, 1000.00, 734878967302979584, 1);
INSERT INTO `t_order_1` VALUES (7348789698531164175, 1000.00, 734878967302979585, 1);
INSERT INTO `t_order_1` VALUES (7348789698698936324, 1000.00, 734878967302979584, 1);
INSERT INTO `t_order_1` VALUES (7348789698908651535, 1000.00, 734878967302979585, 1);
INSERT INTO `t_order_1` VALUES (7348789699118366724, 1000.00, 734878967302979584, 1);
INSERT INTO `t_order_1` VALUES (7348789699286138895, 1000.00, 734878967302979585, 1);
INSERT INTO `t_order_1` VALUES (7348789699453911044, 1000.00, 734878967302979584, 1);
INSERT INTO `t_order_1` VALUES (7348789699621683215, 1000.00, 734878967302979585, 1);
INSERT INTO `t_order_1` VALUES (7348789699831398404, 1000.00, 734878967302979584, 1);
INSERT INTO `t_order_1` VALUES (7348789699999170575, 1000.00, 734878967302979585, 1);
INSERT INTO `t_order_1` VALUES (7348803569136435220, 1000.00, 734880356913643520, 1);

-- ----------------------------
-- Table structure for t_order_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`  (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `price` decimal(10, 2) NOT NULL COMMENT '订单价格',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户id',
  `status` int(1) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

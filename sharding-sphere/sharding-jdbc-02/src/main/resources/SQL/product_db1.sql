/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : localhost:3306
 Source Schema         : product_db1

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 14/05/2022 14:50:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_base_1
-- ----------------------------
DROP TABLE IF EXISTS `product_base_1`;
CREATE TABLE `product_base_1`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type_id` bigint(20) NULL DEFAULT NULL,
  `price` bigint(20) NULL DEFAULT NULL COMMENT '价格',
  `origin_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产地',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_base_2
-- ----------------------------
DROP TABLE IF EXISTS `product_base_2`;
CREATE TABLE `product_base_2`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type_id` bigint(20) NULL DEFAULT NULL,
  `price` bigint(20) NULL DEFAULT NULL COMMENT '价格',
  `origin_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产地',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_description_1
-- ----------------------------
DROP TABLE IF EXISTS `product_description_1`;
CREATE TABLE `product_description_1`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述内容',
  `shop_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_description_2
-- ----------------------------
DROP TABLE IF EXISTS `product_description_2`;
CREATE TABLE `product_description_2`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述内容',
  `shop_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `type_id` bigint(20) NOT NULL COMMENT '商品类型ID',
  `type_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '商品类型名称',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

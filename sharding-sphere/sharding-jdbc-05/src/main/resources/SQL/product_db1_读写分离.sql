/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.47.149-master
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.47.149:3306
 Source Schema         : product_db1

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/05/2022 18:44:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_base
-- ----------------------------
DROP TABLE IF EXISTS `product_base`;
CREATE TABLE `product_base`  (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type_id` bigint(20) NULL DEFAULT NULL,
  `price` bigint(20) NULL DEFAULT NULL COMMENT '价格',
  `origin_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产地',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_base
-- ----------------------------
INSERT INTO `product_base` VALUES (735190791680425984, 35, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794301865984, 59, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794343809024, 95, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794377363456, 42, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794398334976, 48, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794431889408, 39, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794457055232, 63, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794486415360, 76, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794511581184, 73, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735190794532552704, 1, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198411099209728, 25, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198413796147200, 4, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198413825507328, 67, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198413863256064, 10, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198413896810496, 70, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198413951336448, 44, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198414005862400, 30, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198414161051648, 85, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198414190411776, 6, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');
INSERT INTO `product_base` VALUES (735198414223966208, 90, 'Spring Cloud Alibaba实战课程', 1, 159, '码猿技术专栏');

SET FOREIGN_KEY_CHECKS = 1;

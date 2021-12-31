/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : localhost:3306
 Source Schema         : oauth-auth-server

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 05/01/2022 13:30:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认false，是否自动授权',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('myjszl', 'res1', '$2a$10$HWuOIx8C.YvlhLwp2j5LYe/r8B04xtcFmuu6t1XEBrnr2JLGFcc0q', 'all', 'authorization_code,client_credentials,implicit,refresh_token,password', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL权限标识',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE,
  INDEX `id_2`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '订单详情', 'POST:/order/info', '2021-02-02 14:16:07', '2021-06-16 22:25:24');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态：0-正常；1-停用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, '2021-05-21 14:56:51', '2018-12-23 16:00:00');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 1, '2021-03-25 12:39:54', '2018-12-23 16:00:00');
INSERT INTO `sys_role` VALUES (3, '访问用户', 'GUEST', 1, '2021-05-26 15:49:05', '2019-05-05 16:00:00');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(11) NULL DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 3, 1);
INSERT INTO `sys_role_permission` VALUES (2, 2, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别：1-男 2-女',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态：1-正常 0-禁用',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'abcdcdcd', 'user', '用户', 2, '$2a$10$8ecKl8JVIgA39pknoixkjOC4FRz0CJwYItS7UU0Y5zOa0wZN45CqS', '', '17621590365', 1, '123@163.com', '2019-10-10 13:41:22', '2019-10-10 13:41:22');
INSERT INTO `sys_user` VALUES (2, 'dddd12345', 'admin', '系统管理员', 1, '$2a$10$8ecKl8JVIgA39pknoixkjOC4FRz0CJwYItS7UU0Y5zOa0wZN45CqS', 'https://gitee.com/haoxr/image/raw/master/20210605215800.png', '17621210366', 1, '123@163.com', '2019-10-10 13:41:22', '2021-06-06 23:41:35');
INSERT INTO `sys_user` VALUES (3, 'abd345l24dttt', 'test', '测试小用户', 1, '$2a$10$8ecKl8JVIgA39pknoixkjOC4FRz0CJwYItS7UU0Y5zOa0wZN45CqS', 'https://gitee.com/haoxr/image/raw/master/20210605215800.png', '17621210366', 1, '123@163.com', '2021-06-05 01:31:29', '2021-06-05 01:31:29');
INSERT INTO `sys_user` VALUES (4, 'dkdiek6t', 'root', '超级管理员', 1, '$2a$10$8ecKl8JVIgA39pknoixkjOC4FRz0CJwYItS7UU0Y5zOa0wZN45CqS', 'https://gitee.com/haoxr/image/raw/master/20210605215800.png', '17621210366', 1, '123@163.com', '2021-06-05 01:31:29', '2021-06-05 01:31:29');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 3);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3, 3);
INSERT INTO `sys_user_role` VALUES (4, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;

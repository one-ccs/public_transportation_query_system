/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : public_transportation_query_system

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 07/02/2024 22:58:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `register_datetime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册日期',
  `role` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'user' COMMENT '角色（user、admin）',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态（0 未激活、1 已激活、2 已注销）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'admin', '$2a$10$cS4LSUiw37v7JFbGy2B4M.NB3rdN.eG1PAlB6dRx5oCFw/lS0efGe', NULL, '2024-02-05 14:48:27', 'admin', 1);
INSERT INTO `account` VALUES (2, 'user', '$2a$10$cS4LSUiw37v7JFbGy2B4M.NB3rdN.eG1PAlB6dRx5oCFw/lS0efGe', NULL, '2024-02-06 21:06:07', 'user', 1);

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` tinyint NULL DEFAULT 0 COMMENT '广告类型（）',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `describe` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述',
  `img_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '广告图片链接',
  `jump__url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `start_datetime` datetime NULL DEFAULT NULL COMMENT '开始日期',
  `end_datetime` datetime NULL DEFAULT NULL COMMENT '结束日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ad
-- ----------------------------

-- ----------------------------
-- Table structure for lost
-- ----------------------------
DROP TABLE IF EXISTS `lost`;
CREATE TABLE `lost`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `describe` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述',
  `img_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '拾取地点',
  `pick_datetime` datetime NULL DEFAULT NULL COMMENT '拾取时间',
  `release_datetime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '认领状态（0 待认领、1 已认领）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lost
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '内容',
  `release_datetime` datetime NULL DEFAULT NULL COMMENT '发布日期',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态（0 不显示、1 显示）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `no` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '线路号',
  `first_time` time NULL DEFAULT NULL COMMENT '首班车时间',
  `last_time` time NULL DEFAULT NULL COMMENT '末班车时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '开通状态（0 未开通、1 已开通、2 暂停运营）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `no`(`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------

-- ----------------------------
-- Table structure for route_station
-- ----------------------------
DROP TABLE IF EXISTS `route_station`;
CREATE TABLE `route_station`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NULL DEFAULT NULL COMMENT '线路信息id',
  `station_id` int NULL DEFAULT NULL COMMENT '站点信息id',
  `sequence` tinyint NULL DEFAULT NULL COMMENT '站点在线路中的顺序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `f_route_id`(`route_id`) USING BTREE,
  INDEX `f_station_id`(`station_id`) USING BTREE,
  CONSTRAINT `f_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f_station_id` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route_station
-- ----------------------------

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sitename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '站点名',
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '未开通' COMMENT '开通状态',
  `longitude` float NULL DEFAULT NULL COMMENT '经度',
  `latitude` float NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of station
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

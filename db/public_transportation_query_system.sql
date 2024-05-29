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

 Date: 25/05/2024 12:12:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lost
-- ----------------------------
DROP TABLE IF EXISTS `lost`;
CREATE TABLE `lost`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '失物招领id',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '认领状态（0 待认领、1 已认领）',
  `describe` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '描述',
  `img_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '拾取地点',
  `pick_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '拾取时间',
  `claim_datetime` datetime NULL DEFAULT NULL COMMENT '认领时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '失物招领表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lost
-- ----------------------------
INSERT INTO `lost` VALUES (28, 1, '钱包一个', 'uYrKGWavZgZDRNul.webp', '图书馆', '2024-05-01 00:00:00', NULL);
INSERT INTO `lost` VALUES (29, 1, '钱包一个', 'tMIGvTxwvr5sWXHz.webp', '食堂', '2024-05-01 00:00:00', NULL);
INSERT INTO `lost` VALUES (30, 1, '手机一个', 'AjY1wPsZouZ1m77o.webp', '操场', '2024-05-01 00:00:00', NULL);
INSERT INTO `lost` VALUES (31, 0, '《工程数学》一本', 'b1h7SJiK1pmJ371c.jpeg', '101教室', '2024-05-09 00:00:00', NULL);
INSERT INTO `lost` VALUES (32, 0, '水杯1个', '0RaaqG7W949iMDu0.jpeg', '213教室', '2024-05-07 00:00:00', NULL);
INSERT INTO `lost` VALUES (43, 0, '铅笔一把', 'cesPUFPqeYStJ49E.webp', '501教室', '2024-05-08 00:00:00', NULL);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `user_id` int NOT NULL COMMENT '发布用户 id',
  `title` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `release_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布日期',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0 不显示、1 显示）',
  `type` tinyint NULL DEFAULT 0 COMMENT '通知类型（0 系统通知）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (14, 2, '线路添加通知', '系统管理员 admin 于 2024-05-17 12:26:26 新增线路 911', '2024-05-17 12:26:26', 1, 0);
INSERT INTO `notice` VALUES (16, 2, '站点移除通知', '系统管理员 admin 于 2024-05-17 12:38:07 移除站点 四龙碑', '2024-05-17 12:38:07', 1, 0);
INSERT INTO `notice` VALUES (17, 2, '站点添加通知', '系统管理员 admin 于 2024-05-18 00:37:41 新增站点 213', '2024-05-18 00:37:41', 1, 0);
INSERT INTO `notice` VALUES (18, 2, NULL, '[服务热线]：公交集团:本地号码:16866666，外地号码:023-16866666；通卡公司:95105111.\\n[票价信息]：一票制2元、无人售票线路。可刷卡、投币，支持移动支付。\\n[换乘优惠]：畅通卡9折，纳入“一小时免费换乘”，扫码支付暂无优惠。\\n[所属公司]：重庆公交集团西部公交公司', '2024-05-18 18:27:51', 1, 1);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `series` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '序号',
  `username` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `token` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '令牌',
  `last_used` timestamp NOT NULL COMMENT '上次使用时间',
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'rememberMe 持久化表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('/JO64SRDE4RR6D5NgYqw7A==', 'admin', 'YUQiy64uNwODtxahPLlBwA==', '2024-03-26 14:51:00');
INSERT INTO `persistent_logins` VALUES ('8SY6pxpjcgwJojn07WOlug==', 'admin', 'RNcCM5xKTDHzB+5B0c4Iow==', '2024-03-26 15:06:13');
INSERT INTO `persistent_logins` VALUES ('A1V4Y9UrNzLg559Dyfpqwg==', 'admin', 'jR3bAppLbyILKpdkssnGMA==', '2024-02-26 15:52:20');
INSERT INTO `persistent_logins` VALUES ('cJIkZMdXTGh5OtbwJ5v02w==', 'admin', 'FEOpOKSS52hfO2Yr/Ns0oQ==', '2024-05-05 10:49:46');
INSERT INTO `persistent_logins` VALUES ('GHDUqWqmgoz1q//i0sf5Cw==', 'admin', 'RrhIW+gD1ybqvEn1PqrQzw==', '2024-04-01 02:57:26');
INSERT INTO `persistent_logins` VALUES ('gmkNXrsL8hWvEVxiOQMFCg==', 'admin', 'Cc0dXOA+ILHFWJQim0wNYw==', '2024-03-26 12:35:38');
INSERT INTO `persistent_logins` VALUES ('IHBMuiOXAsg/zczj3L2qZQ==', 'admin', 'Tsrh9SgTvWscmfwV2g9xkg==', '2024-03-26 15:05:51');
INSERT INTO `persistent_logins` VALUES ('J598h5wttrPEVDfz0ng2Pg==', 'admin', 'MuJTikRtIZVJoR+GKT2uzw==', '2024-05-05 11:15:15');
INSERT INTO `persistent_logins` VALUES ('jpZ54gyW0h8ywe+1kvdFcg==', 'admin', 'tiGakkWC9ifoosI0Rey09w==', '2024-04-18 01:52:58');
INSERT INTO `persistent_logins` VALUES ('k7RnsU6gitv4KlZiCxd9Tw==', 'admin', 'Ovs30ZAbkc2w9GJVRxzQQA==', '2024-05-24 03:22:00');
INSERT INTO `persistent_logins` VALUES ('kOMWco9K3Dy1SWn3Dd7qsA==', 'admin', 'rqNVRn6f1nVd1cVbt9KOAA==', '2024-04-01 03:01:26');
INSERT INTO `persistent_logins` VALUES ('KqX4km1yOAbJjHxndGUIOA==', 'admin', '/4Y93FNPGhGROCyPimyCRw==', '2024-03-28 06:49:26');
INSERT INTO `persistent_logins` VALUES ('lqZXjZJoHPRctq1E+GXVaQ==', 'admin', 'sDH0Ydr62hYCorZkreY1Rw==', '2024-02-27 06:58:19');
INSERT INTO `persistent_logins` VALUES ('macdkWN5rVR0mHlCNOX5Tg==', 'admin', 'rsMHiI9EYie35NFohcYbIw==', '2024-04-01 02:55:16');
INSERT INTO `persistent_logins` VALUES ('mimfy/7h9XIV+yvCtiVPGg==', 'admin', '3gLCKneDmGhsN/or0wGO+A==', '2024-02-26 15:52:40');
INSERT INTO `persistent_logins` VALUES ('mSgr12ktvKSc+SX3aUJL4Q==', 'admin', 'IQfEd3VsId3Vj8itVoUOnw==', '2024-02-27 04:42:12');
INSERT INTO `persistent_logins` VALUES ('p4CK6Hr7ZzrIrWBqeYT51Q==', 'admin', 'IIs8aK+c25R3dycKC1ltcQ==', '2024-05-14 13:45:31');
INSERT INTO `persistent_logins` VALUES ('Q5oyzR1yRrlzAVeUHhJgmw==', 'admin', '4fPaSVycrHLwDrCcjoScvg==', '2024-03-27 08:15:13');
INSERT INTO `persistent_logins` VALUES ('XTQo4QcencfvUc6qnfRrtg==', 'admin', 'jwUdl95GdbODhnNeKt9Kkg==', '2024-02-27 01:39:35');
INSERT INTO `persistent_logins` VALUES ('yAO4aRP6eKLKmn3LGr6pbg==', 'admin', 'ALqawGtUtmSUK1NifNVwpQ==', '2024-03-26 14:04:24');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色',
  `name_zh` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '中文名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `name_zh`(`name_zh`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'user', '用户');
INSERT INTO `role` VALUES (2, 'admin', '管理员');
INSERT INTO `role` VALUES (3, 'userAdmin', '用户管理员');
INSERT INTO `role` VALUES (4, 'systemAdmin', '系统管理员');
INSERT INTO `role` VALUES (5, 'superAdmin', '超级管理员');

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '线路id',
  `no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '线路号',
  `price` decimal(4, 2) NULL DEFAULT 2.00 COMMENT '票价',
  `first_time` time NULL DEFAULT NULL COMMENT '首班车时间',
  `last_time` time NULL DEFAULT NULL COMMENT '末班车时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '开通状态（0 计划开通、1 正常运营、2 暂停运营）',
  `opening_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开通日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '线路表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (1, '7526-6', 2.00, '22:05:00', '12:02:00', 1, '2024-03-27 16:22:26');
INSERT INTO `route` VALUES (2, '4887-0', 2.00, '18:04:00', '00:10:00', 1, '2024-03-27 16:22:26');
INSERT INTO `route` VALUES (3, '763-4', 2.00, '07:08:00', '02:05:00', 0, '2024-03-04 07:03:03');
INSERT INTO `route` VALUES (16, '911', 2.00, '08:00:00', '18:45:00', 1, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (17, '322', 2.00, '08:00:00', '18:45:00', 0, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (18, '457', 2.00, '08:00:00', '18:45:00', 0, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (26, '230', 2.00, '06:00:00', '22:00:00', 1, '2024-05-18 01:30:36');
INSERT INTO `route` VALUES (27, '275', 2.00, '06:00:00', '00:00:00', 1, '2024-05-18 01:30:36');
INSERT INTO `route` VALUES (28, '490', 2.00, '06:30:00', '19:30:00', 0, '2024-05-18 01:30:36');
INSERT INTO `route` VALUES (29, '413', 2.00, '06:00:00', '21:30:00', 0, '2024-05-18 10:14:34');
INSERT INTO `route` VALUES (30, '418', 2.00, '06:30:00', '21:00:00', 0, '2024-05-18 10:14:34');
INSERT INTO `route` VALUES (31, '420', 2.00, '06:20:00', '20:20:00', 0, '2024-05-18 10:14:34');
INSERT INTO `route` VALUES (32, '132', 2.00, '07:00:00', '20:00:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (33, '164', 2.00, '06:20:00', '18:30:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (34, '165', 2.00, '06:00:00', '17:40:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (35, '162', 2.00, '07:00:00', '17:40:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (36, 'r89183', 2.00, '06:00:00', '19:00:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (37, 'r89184', 2.00, '06:00:00', '15:30:00', 0, '2024-05-18 13:13:22');
INSERT INTO `route` VALUES (38, 'r21893', 2.00, '05:30:00', '18:30:00', 0, '2024-05-20 12:23:09');
INSERT INTO `route` VALUES (39, 'r89182', 2.00, '06:30:00', '17:00:00', 0, '2024-05-20 12:23:09');
INSERT INTO `route` VALUES (40, '914', 2.00, '06:00:00', '20:00:00', 1, '2024-05-20 12:24:09');

-- ----------------------------
-- Table structure for route_station
-- ----------------------------
DROP TABLE IF EXISTS `route_station`;
CREATE TABLE `route_station`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL COMMENT '线路 id（1）',
  `station_id` int NOT NULL COMMENT '站点 id（n）',
  `sequence` tinyint NOT NULL COMMENT '站点在线路中的顺序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `route_id`(`route_id`) USING BTREE,
  INDEX `station_id`(`station_id`) USING BTREE,
  CONSTRAINT `route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `station_id` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 192 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '线路_关联表站点' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route_station
-- ----------------------------
INSERT INTO `route_station` VALUES (63, 26, 101, 1);
INSERT INTO `route_station` VALUES (64, 26, 102, 2);
INSERT INTO `route_station` VALUES (65, 26, 103, 3);
INSERT INTO `route_station` VALUES (66, 26, 104, 4);
INSERT INTO `route_station` VALUES (67, 27, 103, 1);
INSERT INTO `route_station` VALUES (68, 27, 101, 2);
INSERT INTO `route_station` VALUES (69, 27, 99, 3);
INSERT INTO `route_station` VALUES (70, 28, 104, 1);
INSERT INTO `route_station` VALUES (71, 28, 105, 2);
INSERT INTO `route_station` VALUES (72, 28, 99, 3);
INSERT INTO `route_station` VALUES (73, 28, 100, 4);
INSERT INTO `route_station` VALUES (74, 29, 94, 1);
INSERT INTO `route_station` VALUES (75, 29, 95, 2);
INSERT INTO `route_station` VALUES (76, 29, 97, 3);
INSERT INTO `route_station` VALUES (97, 3, 110, 1);
INSERT INTO `route_station` VALUES (98, 3, 107, 2);
INSERT INTO `route_station` VALUES (99, 3, 108, 3);
INSERT INTO `route_station` VALUES (128, 16, 112, 1);
INSERT INTO `route_station` VALUES (129, 16, 113, 2);
INSERT INTO `route_station` VALUES (130, 16, 114, 3);
INSERT INTO `route_station` VALUES (131, 16, 116, 4);
INSERT INTO `route_station` VALUES (132, 16, 115, 5);
INSERT INTO `route_station` VALUES (133, 16, 117, 6);
INSERT INTO `route_station` VALUES (134, 16, 111, 7);
INSERT INTO `route_station` VALUES (135, 16, 110, 8);
INSERT INTO `route_station` VALUES (136, 16, 109, 9);
INSERT INTO `route_station` VALUES (137, 16, 108, 10);
INSERT INTO `route_station` VALUES (138, 16, 107, 11);
INSERT INTO `route_station` VALUES (139, 16, 106, 12);
INSERT INTO `route_station` VALUES (140, 16, 105, 13);
INSERT INTO `route_station` VALUES (141, 16, 104, 14);
INSERT INTO `route_station` VALUES (142, 16, 103, 15);
INSERT INTO `route_station` VALUES (143, 16, 102, 16);
INSERT INTO `route_station` VALUES (144, 16, 101, 17);
INSERT INTO `route_station` VALUES (145, 16, 100, 18);
INSERT INTO `route_station` VALUES (146, 16, 99, 19);
INSERT INTO `route_station` VALUES (147, 16, 98, 20);
INSERT INTO `route_station` VALUES (148, 16, 97, 21);
INSERT INTO `route_station` VALUES (149, 16, 96, 22);
INSERT INTO `route_station` VALUES (150, 16, 95, 23);
INSERT INTO `route_station` VALUES (151, 16, 94, 24);
INSERT INTO `route_station` VALUES (161, 40, 116, 1);
INSERT INTO `route_station` VALUES (162, 40, 117, 2);
INSERT INTO `route_station` VALUES (163, 40, 115, 3);
INSERT INTO `route_station` VALUES (164, 40, 114, 4);
INSERT INTO `route_station` VALUES (165, 40, 113, 5);
INSERT INTO `route_station` VALUES (166, 40, 112, 6);
INSERT INTO `route_station` VALUES (167, 40, 111, 7);
INSERT INTO `route_station` VALUES (168, 17, 112, 1);
INSERT INTO `route_station` VALUES (169, 17, 113, 2);
INSERT INTO `route_station` VALUES (170, 2, 111, 1);
INSERT INTO `route_station` VALUES (171, 2, 110, 2);
INSERT INTO `route_station` VALUES (172, 2, 109, 3);
INSERT INTO `route_station` VALUES (173, 2, 108, 4);
INSERT INTO `route_station` VALUES (174, 2, 107, 5);
INSERT INTO `route_station` VALUES (175, 2, 106, 6);
INSERT INTO `route_station` VALUES (176, 2, 105, 7);
INSERT INTO `route_station` VALUES (177, 2, 104, 8);
INSERT INTO `route_station` VALUES (178, 2, 103, 9);
INSERT INTO `route_station` VALUES (179, 2, 102, 10);
INSERT INTO `route_station` VALUES (180, 2, 101, 11);
INSERT INTO `route_station` VALUES (181, 2, 100, 12);
INSERT INTO `route_station` VALUES (182, 2, 99, 13);
INSERT INTO `route_station` VALUES (183, 2, 98, 14);
INSERT INTO `route_station` VALUES (184, 2, 97, 15);
INSERT INTO `route_station` VALUES (185, 2, 96, 16);
INSERT INTO `route_station` VALUES (186, 2, 95, 17);
INSERT INTO `route_station` VALUES (187, 2, 94, 18);
INSERT INTO `route_station` VALUES (188, 1, 95, 1);
INSERT INTO `route_station` VALUES (189, 1, 108, 2);
INSERT INTO `route_station` VALUES (190, 1, 98, 3);
INSERT INTO `route_station` VALUES (191, 1, 110, 4);

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '站点id',
  `sitename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '站点名',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '纬度',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '开通状态（0 计划开通、1 正常运营、2 暂停运营）',
  `opening_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开通日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '站点表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES (94, '大黄路口', 106.52917176270236, 29.544293960169778, 1, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (95, '大黄路', 106.52844339016097, 29.542614985384052, 1, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (96, '肖家湾①', 106.53211268656632, 29.547855339442197, 1, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (97, '半岛深蓝', 106.53062369825061, 29.5408413398065, 1, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (98, '学堂湾', 106.5327262824633, 29.548073587552913, 1, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (99, '大坪医院', 106.5282496164479, 29.548055155826397, 0, '2024-05-18 00:39:22');
INSERT INTO `station` VALUES (100, '九校', 106.49574236173912, 29.492713188243606, 1, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (101, '跃进路·小豆子艺培中心', 106.4970919822143, 29.492879100051795, 0, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (102, '建设村', 106.49382771191155, 29.494328588487505, 0, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (103, '跃进村', 106.49886798389984, 29.493049891923075, 1, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (104, '轨道平安站', 106.49146208972314, 29.49512298272771, 1, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (105, '新山村', 106.4951175436033, 29.489511386364082, 1, '2024-05-18 01:03:59');
INSERT INTO `station` VALUES (106, '江洲路', 106.53396136892576, 29.384183415670662, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (107, '云山路（无站牌）', 106.53121273003315, 29.385716818933687, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (108, '南城国际', 106.53299863801632, 29.382278688958948, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (109, '新民街口', 106.5323036057586, 29.386012066551373, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (110, '轨道金竹站', 106.53120339843413, 29.381945216890855, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (111, '巴南亲水公园', 106.53448305144978, 29.382030101678122, 1, '2024-05-18 13:13:22');
INSERT INTO `station` VALUES (112, '鸡公台', 106.53962875115394, 29.344384395150854, 1, '2024-05-20 12:23:09');
INSERT INTO `station` VALUES (113, '四龙碑', 106.53984637397708, 29.34655874141637, 1, '2024-05-20 12:23:09');
INSERT INTO `station` VALUES (114, '鸡天路', 106.53816321956491, 29.340032668990823, 1, '2024-05-20 12:23:09');
INSERT INTO `station` VALUES (115, '观音店', 106.53852725051885, 29.339547405154203, 1, '2024-05-20 12:23:09');
INSERT INTO `station` VALUES (116, '砖房', 106.55304602379772, 29.33887029080134, 0, '2024-05-20 12:23:09');
INSERT INTO `station` VALUES (117, '崇义路', 106.53635414564809, 29.346788360183982, 0, '2024-05-20 12:23:09');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像文件名',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0 未激活、1 已激活、2 已注销）',
  `register_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'superadmin', '$2a$10$VhVY9ZT644BZHkfrHOrVlO9SBojkrXozvpR8/9wopH85Wu/uQ4RK.', '123d@163.com', NULL, 1, '2024-02-05 14:48:27');
INSERT INTO `user` VALUES (2, 'admin', '$2a$10$zY6k8dV3WFZiwtMItHUJGO.7e6EVs6aiS7Aw4igBszhL4nNsvGwUi', NULL, 'FkJynhkJS6e6zu1a.gif', 1, '2024-02-06 21:06:07');
INSERT INTO `user` VALUES (3, 'user', '$2a$10$QmycXUmpKwtfHcJByIDzqeEHRI13htAygzcUMto0J0li15trEyJia', NULL, NULL, 0, '2024-02-09 16:13:15');
INSERT INTO `user` VALUES (151, 'useradmin', '$2a$10$zY6k8dV3WFZiwtMItHUJGO.7e6EVs6aiS7Aw4igBszhL4nNsvGwUi', NULL, NULL, 1, '2024-02-25 16:10:03');
INSERT INTO `user` VALUES (158, 'testuser', '$2a$10$tDS.Hy4TGn9QFWT0KJ2l4OHaGnDwVnkBQ54I6Z3SCucP/QvCkl3tK', 'f.rjlsxsffag@xkqrfsut.lr', NULL, 1, '2024-02-26 23:48:11');
INSERT INTO `user` VALUES (165, 'sysadmin', '$2a$10$Y9IXO1cQiz6lwBPYydYj6OHOAmKQF3x03Hmt6Se2F.mCRjVCpmsga', NULL, NULL, 1, '2024-04-24 16:48:08');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL COMMENT '用户id',
  `rid` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `rid` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 386 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户_角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (358, 165, 4);
INSERT INTO `user_role` VALUES (366, 2, 2);
INSERT INTO `user_role` VALUES (367, 2, 3);
INSERT INTO `user_role` VALUES (368, 2, 4);
INSERT INTO `user_role` VALUES (377, 151, 3);
INSERT INTO `user_role` VALUES (378, 151, 1);
INSERT INTO `user_role` VALUES (379, 3, 1);
INSERT INTO `user_role` VALUES (380, 158, 1);
INSERT INTO `user_role` VALUES (381, 1, 5);

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `prevent_empty_email_insert`;
delimiter ;;
CREATE TRIGGER `prevent_empty_email_insert` BEFORE INSERT ON `user` FOR EACH ROW BEGIN
    IF NEW.email = '' THEN
        SET NEW.email = NULL;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `prevent_empty_email_update`;
delimiter ;;
CREATE TRIGGER `prevent_empty_email_update` BEFORE UPDATE ON `user` FOR EACH ROW BEGIN
    IF NEW.email = '' THEN
        SET NEW.email = NULL;
    END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

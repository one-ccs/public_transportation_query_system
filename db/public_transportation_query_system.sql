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

 Date: 14/05/2024 18:35:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `type` tinyint NULL DEFAULT 0 COMMENT '广告类型',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `describe` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述',
  `img_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '广告图片链接',
  `jump_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `start_datetime` datetime NOT NULL COMMENT '开始日期',
  `end_datetime` datetime NOT NULL COMMENT '结束日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '广告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ad
-- ----------------------------
INSERT INTO `ad` VALUES (4, 2, '大争包装体文', '由具保支快保又太通而确已连。', 'http://dummyimage.com/728x90', 'http://riixnbl.st/mloljs', '2013-01-22 20:19:37', '1971-04-04 03:01:30');
INSERT INTO `ad` VALUES (5, 4, '道究从位向专等', '也属制验西打点见指动积日量所只经进。', 'http://dummyimage.com/468x60', 'http://hwqs.vc/arc', '1983-10-02 10:34:49', '1986-04-07 16:42:26');
INSERT INTO `ad` VALUES (6, 9, '军即离应气万', '公周身不数而经器表一局区会经员易。', 'http://dummyimage.com/120x240', 'http://hhewknb.ms/yyr', '2017-05-11 18:14:18', '2009-07-06 16:20:56');
INSERT INTO `ad` VALUES (7, 8, '比因织农有', '把则规象方消影总头面属起回。', 'http://dummyimage.com/234x60', 'http://vdvtud.za/fektosgx', '1978-10-26 08:43:51', '1986-08-30 17:03:23');
INSERT INTO `ad` VALUES (8, 2, '格实件热行局眼', '先再多革口新目作平整路规东容量众称九。', 'http://dummyimage.com/160x600', 'http://qmw.mil/pjwbqzyo', '2008-12-08 14:07:32', '2018-02-03 03:06:17');
INSERT INTO `ad` VALUES (9, 8, '层使装总品些是', '音影见统分住提立重很界之做发华适便真。', 'http://dummyimage.com/160x600', 'http://qyrlifro.pk/uhebduyj', '1987-10-09 09:42:21', '1970-02-19 14:30:22');
INSERT INTO `ad` VALUES (10, 5, '象常精西个', '物观回每龙下运共合矿节列向。', 'http://dummyimage.com/88x31', 'http://gffgcif.cd/xgwyx', '1997-08-08 02:36:02', '2008-08-08 11:25:29');
INSERT INTO `ad` VALUES (11, 6, '九温小以', '使因商保北决从听问整效统民采导基。', 'http://dummyimage.com/234x60', 'http://osvqagfhd.bb/iyio', '2012-10-25 22:28:26', '1997-04-11 01:57:27');
INSERT INTO `ad` VALUES (12, 6, '也快理办资立向', '构拉飞儿心走变权没派较强。', 'http://dummyimage.com/336x280', 'http://wnhij.bg/yfngs', '1997-06-27 02:36:34', '2012-09-09 15:09:25');
INSERT INTO `ad` VALUES (13, 3, '位今中流员', '那状难格图易南接影术给写真代放花。', 'http://dummyimage.com/728x90', 'http://kqtygz.sm/pqljngieu', '1977-07-04 02:14:35', '1986-06-15 17:19:36');
INSERT INTO `ad` VALUES (14, 4, '做内角效通太', '亲算军地反面被无开划年者清对。', 'http://dummyimage.com/125x125', 'http://exdghjhfj.sb/fmzq', '1986-09-05 22:31:27', '1977-05-30 18:33:44');

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '失物招领表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lost
-- ----------------------------
INSERT INTO `lost` VALUES (4, 0, '确期号群者边打观建资低实义维国。', 'http://dummyimage.com/468x60', '命题品眼标本县片量身众应月。', '1989-07-02 09:58:13', NULL);
INSERT INTO `lost` VALUES (5, 0, '期满感听动率通只亲美样过京须厂。', 'http://dummyimage.com/250x250', '共它人决完型如基东党已斯农接元别选。', '2008-11-26 06:58:35', NULL);
INSERT INTO `lost` VALUES (6, 0, '三克回义证亲节风年三深天感。', 'http://dummyimage.com/300x250', '进导解图切它六包始每当率报收。', '2010-04-17 13:07:31', NULL);
INSERT INTO `lost` VALUES (7, 1, '通年者部战接除住期斗天场。', 'http://dummyimage.com/180x150', '省百万山表连那定林论数基变平极没。', '1978-09-21 05:32:37', NULL);
INSERT INTO `lost` VALUES (8, 1, '快非叫两精调确以干了置太便。', 'http://dummyimage.com/180x150', '出转海体可给国飞路易号务例才回。', '1979-06-20 16:46:29', NULL);
INSERT INTO `lost` VALUES (9, 0, '引全无务步为把完南新式需图。', 'http://dummyimage.com/336x280', '口家由小花门速基造型此素江具听示。', '1980-04-14 22:12:55', NULL);

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (4, 1, NULL, '每引正眼土原维思被转型受铁局放任每此。飞候质织济信准出走关选道质解究指。除记全对调向加技学还六铁专周了状。明建什自场产族技那联产始比。再命厂市确图维民最者求工关打光并关。作石式斗他建天战强七有话究。', '1982-10-23 04:53:39', 1);
INSERT INTO `notice` VALUES (5, 1, NULL, '声状新期想原她史边国边起。精构什每革验对而石斯共要山米界。关见类计级厂党界导知家属特热据圆约。', '2024-02-27 13:26:01', 1);
INSERT INTO `notice` VALUES (6, 2, '断己立体接', '人组目而百林论数运包或统系经式论那光。斯起安复口量须金感发后六习状明术张。体织平精约正料得采军是素可开拉。平式除层解究安统角须到查争效段。层温总期常华说照每报青音建九月。织级前体值最团真严定口型。行三化安约此料头治教约门采。', '2024-03-26 22:56:16', 1);
INSERT INTO `notice` VALUES (7, 2, '面族京选入', '强两又复律效济发天好需报她高火比。切装劳始信系很好更动此得变今。时林状全明部四容机加段制前研将实很。议精成般选果阶复看需西须出市况历高。原角儿领维米备林清展线改我。几打子青局论我转家先使专世切她需。', '2024-03-26 22:57:02', 1);
INSERT INTO `notice` VALUES (8, 2, '123', '3211111', '2024-03-26 22:57:03', 0);
INSERT INTO `notice` VALUES (9, 2, '123', '321', '2024-03-26 22:57:04', 1);

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
INSERT INTO `persistent_logins` VALUES ('kOMWco9K3Dy1SWn3Dd7qsA==', 'admin', 'rqNVRn6f1nVd1cVbt9KOAA==', '2024-04-01 03:01:26');
INSERT INTO `persistent_logins` VALUES ('KqX4km1yOAbJjHxndGUIOA==', 'admin', '/4Y93FNPGhGROCyPimyCRw==', '2024-03-28 06:49:26');
INSERT INTO `persistent_logins` VALUES ('lqZXjZJoHPRctq1E+GXVaQ==', 'admin', 'sDH0Ydr62hYCorZkreY1Rw==', '2024-02-27 06:58:19');
INSERT INTO `persistent_logins` VALUES ('macdkWN5rVR0mHlCNOX5Tg==', 'admin', 'rsMHiI9EYie35NFohcYbIw==', '2024-04-01 02:55:16');
INSERT INTO `persistent_logins` VALUES ('mimfy/7h9XIV+yvCtiVPGg==', 'admin', '3gLCKneDmGhsN/or0wGO+A==', '2024-02-26 15:52:40');
INSERT INTO `persistent_logins` VALUES ('mSgr12ktvKSc+SX3aUJL4Q==', 'admin', 'IQfEd3VsId3Vj8itVoUOnw==', '2024-02-27 04:42:12');
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
  `first_time` time NULL DEFAULT NULL COMMENT '首班车时间',
  `last_time` time NULL DEFAULT NULL COMMENT '末班车时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '开通状态（0 计划开通、1 正常运营、2 暂停运营）',
  `opening_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开通日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '线路表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (1, '7526-6', '22:05:00', '12:02:00', 1, '2024-03-27 16:22:26');
INSERT INTO `route` VALUES (2, '4887-0', '18:04:00', '00:10:00', 0, '2024-03-27 16:22:26');
INSERT INTO `route` VALUES (3, '763-4', '07:08:00', '02:05:00', 0, '2024-03-04 07:03:03');
INSERT INTO `route` VALUES (15, '914', '08:00:00', '18:45:00', 1, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (16, '911', '08:00:00', '18:45:00', 1, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (17, '322', '08:00:00', '18:45:00', 1, '2024-05-05 19:14:44');
INSERT INTO `route` VALUES (18, '457', '08:00:00', '18:45:00', 1, '2024-05-05 19:14:44');

-- ----------------------------
-- Table structure for route_station
-- ----------------------------
DROP TABLE IF EXISTS `route_station`;
CREATE TABLE `route_station`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL COMMENT '线路信息id',
  `station_id` int NOT NULL COMMENT '站点信息id',
  `sequence` tinyint NOT NULL COMMENT '站点在线路中的顺序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `route_id`(`route_id`) USING BTREE,
  INDEX `station_id`(`station_id`) USING BTREE,
  CONSTRAINT `route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `station_id` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '线路_关联表站点' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route_station
-- ----------------------------
INSERT INTO `route_station` VALUES (33, 3, 2, 1);
INSERT INTO `route_station` VALUES (34, 1, 1, 1);
INSERT INTO `route_station` VALUES (35, 1, 2, 2);
INSERT INTO `route_station` VALUES (36, 1, 9, 3);
INSERT INTO `route_station` VALUES (37, 15, 1, 1);
INSERT INTO `route_station` VALUES (38, 15, 2, 2);
INSERT INTO `route_station` VALUES (39, 15, 3, 3);
INSERT INTO `route_station` VALUES (40, 15, 9, 4);
INSERT INTO `route_station` VALUES (41, 16, 1, 1);
INSERT INTO `route_station` VALUES (42, 16, 2, 2);
INSERT INTO `route_station` VALUES (43, 16, 3, 3);
INSERT INTO `route_station` VALUES (44, 16, 9, 4);
INSERT INTO `route_station` VALUES (45, 17, 1, 1);
INSERT INTO `route_station` VALUES (46, 17, 2, 2);
INSERT INTO `route_station` VALUES (47, 17, 3, 3);
INSERT INTO `route_station` VALUES (48, 17, 9, 4);
INSERT INTO `route_station` VALUES (49, 18, 1, 1);
INSERT INTO `route_station` VALUES (50, 18, 2, 2);
INSERT INTO `route_station` VALUES (51, 18, 3, 3);
INSERT INTO `route_station` VALUES (52, 18, 9, 4);

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '站点id',
  `sitename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '站点名',
  `longitude` float NULL DEFAULT NULL COMMENT '经度',
  `latitude` float NULL DEFAULT NULL COMMENT '纬度',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '开通状态（0 计划开通、1 正常运营、2 暂停运营）',
  `opening_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开通日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '站点表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES (1, '人和街', 14.3173, 50.477, 0, '2009-02-08 10:30:57');
INSERT INTO `station` VALUES (2, '大礼堂', 16.927, 53.2462, 1, '1975-03-17 00:48:05');
INSERT INTO `station` VALUES (3, '大溪沟', 62.4125, 89, 1, '1978-04-01 15:00:51');
INSERT INTO `station` VALUES (9, '儿童医院后门', 4.47, 81.886, 0, '1991-09-11 14:43:50');
INSERT INTO `station` VALUES (10, '大溪沟静园', 52.4377, 65.28, 1, '2000-11-18 05:06:28');
INSERT INTO `station` VALUES (11, '桂花湾', 3.0851, 62.156, 0, '1986-09-16 04:16:18');
INSERT INTO `station` VALUES (12, '鸡公台', 66.8495, 25.5785, 0, '1972-01-14 09:38:42');
INSERT INTO `station` VALUES (13, '四龙碑', 23.5256, 20.7025, 1, '1994-07-22 21:14:23');
INSERT INTO `station` VALUES (14, '区人民医院', 11.2567, 38.797, 0, '1976-02-09 23:04:00');
INSERT INTO `station` VALUES (15, '龙洲湾枢纽站', 31.6376, 50.64, 0, '2019-03-25 01:05:20');
INSERT INTO `station` VALUES (16, '上式建还细外', 69.855, 27.649, 0, '1993-01-18 14:21:11');
INSERT INTO `station` VALUES (17, '日矿支量', 11.347, 34.2045, 0, '1986-05-17 23:23:35');
INSERT INTO `station` VALUES (18, '思线身题想', 54.992, 67.2294, 1, '2004-05-06 18:16:58');
INSERT INTO `station` VALUES (19, '口确用或广业', 40.7128, 32.827, 0, '2008-08-20 21:18:23');
INSERT INTO `station` VALUES (20, '计结备入', 90, 26.27, 0, '1995-09-22 15:53:37');
INSERT INTO `station` VALUES (21, '民需法名电知', 61.1136, 5.839, 0, '1982-09-21 00:11:39');
INSERT INTO `station` VALUES (22, '么定技全北', 90, 38.187, 0, '1998-02-23 23:26:45');
INSERT INTO `station` VALUES (23, '音单水已心', 8.86993, 1.387, 0, '1993-08-09 19:39:41');
INSERT INTO `station` VALUES (24, '广外性她支', 86.153, 64.3327, 0, '1983-03-20 00:57:52');
INSERT INTO `station` VALUES (25, '计段次们看联', 77.619, 52.525, 1, '1986-06-28 13:46:48');
INSERT INTO `station` VALUES (26, '接料金何积取', 67.0179, 6.24213, 0, '1978-04-01 16:54:19');
INSERT INTO `station` VALUES (27, '知件律新拉', 7.27535, 80.4897, 1, '1999-08-18 20:35:10');
INSERT INTO `station` VALUES (28, '如去教又决', 90, 25.2695, 1, '1995-08-25 09:10:46');
INSERT INTO `station` VALUES (29, '水太面打', 60.6585, 63.73, 0, '1973-05-15 08:04:00');
INSERT INTO `station` VALUES (30, '国机头育儿外里', 8.428, 70.22, 1, '2015-05-30 15:38:16');
INSERT INTO `station` VALUES (31, '取效行', 90, 62.83, 0, '1970-09-13 15:39:47');
INSERT INTO `station` VALUES (32, '共时先取', 75.15, 40.35, 0, '1996-02-25 11:34:48');
INSERT INTO `station` VALUES (33, '也难价', 90, 41.79, 0, '1975-06-01 18:02:17');
INSERT INTO `station` VALUES (34, '难是外原石自', 5.564, 45.4217, 0, '2012-07-16 10:00:59');
INSERT INTO `station` VALUES (35, '话布白为', 36.7777, 85.9739, 0, '2011-08-11 13:14:27');
INSERT INTO `station` VALUES (36, '力王口那看算', 90, 9.64551, 0, '1994-11-08 10:43:02');
INSERT INTO `station` VALUES (37, '非识四议对', 62.4775, 16.47, 0, '1989-01-30 20:37:28');
INSERT INTO `station` VALUES (38, '办听无开们按', 64.1679, 62.8533, 1, '1995-10-14 00:34:33');
INSERT INTO `station` VALUES (39, '北满织此还应历', 35.8104, 57.82, 0, '1972-12-24 18:43:42');
INSERT INTO `station` VALUES (40, '权种山二斯', 41.26, 16.8761, 1, '2023-09-07 21:22:07');
INSERT INTO `station` VALUES (41, '属法向大口矿', 59.757, 81.7383, 1, '1992-06-17 03:52:29');
INSERT INTO `station` VALUES (42, '分易切权西毛', 83.5354, 16.147, 1, '1979-07-12 13:08:00');
INSERT INTO `station` VALUES (43, '自资专年京理于', 90, 59.4584, 0, '2003-06-14 19:02:47');
INSERT INTO `station` VALUES (44, '能成红者平己八', 69.3207, 62.4338, 1, '2015-09-22 20:23:14');
INSERT INTO `station` VALUES (45, '技正又件', 37.898, 82.3465, 0, '2011-03-30 13:59:35');
INSERT INTO `station` VALUES (46, '情质事消线', 24.9013, 50.187, 0, '2012-11-09 15:02:21');
INSERT INTO `station` VALUES (47, '整又切马号看规', 24.0832, 83.827, 0, '1999-12-08 20:06:43');
INSERT INTO `station` VALUES (48, '气院半年', 67.3524, 89, 0, '2004-09-23 19:55:40');
INSERT INTO `station` VALUES (49, '被受时', 34.449, 23.077, 1, '1990-03-10 18:13:42');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `register_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册日期',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0 未激活、1 已激活、2 已注销）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 166 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'superadmin', '$2a$10$VhVY9ZT644BZHkfrHOrVlO9SBojkrXozvpR8/9wopH85Wu/uQ4RK.', '123@163.com', '2024-02-05 14:48:27', 0);
INSERT INTO `user` VALUES (2, 'admin', '$2a$10$zY6k8dV3WFZiwtMItHUJGO.7e6EVs6aiS7Aw4igBszhL4nNsvGwUi', NULL, '2024-02-06 21:06:07', 0);
INSERT INTO `user` VALUES (3, 'user', '$2a$10$QmycXUmpKwtfHcJByIDzqeEHRI13htAygzcUMto0J0li15trEyJia', NULL, '2024-02-09 16:13:15', 1);
INSERT INTO `user` VALUES (111, '侯艳', '$2a$10$yzELdS/wv3E2WbCzRXeGYulLAI2815AWaMMcqJYEHtZZfzmfuwwbG', 'i.nlb@mfswwrpchs.ga', '2024-02-21 20:07:08', 1);
INSERT INTO `user` VALUES (130, '赵娟', '202cb962ac59075b964b07152d234b70', 'r.tfufwxjix@sort.ke', '2024-02-21 22:09:07', 1);
INSERT INTO `user` VALUES (151, 'userandmin', '$2a$10$nbfhYq5.7dtzjS1ds8Nqz.58kVYX4Hg58W6EbbvjfLV7ndYKFDcxG', NULL, '2024-02-25 16:10:03', 1);
INSERT INTO `user` VALUES (158, '段杰', '$2a$10$QQi4bgfLiWRuqQQD1s6Aaeda.V4pw3NVBgJm82X1S2xAM/Jl6tP9q', 'f.rjlsxsffag@xkqrfsut.lr', '2024-02-26 23:48:11', 1);
INSERT INTO `user` VALUES (163, '13', '$2a$10$K.qU5VAnFPK0U3iaPTT2VuJOmp/BJXysAAFDcEhc1Eqs3gKu0WcVm', NULL, '2024-04-24 15:24:42', 1);
INSERT INTO `user` VALUES (165, 'sysadmin', '$2a$10$Y9IXO1cQiz6lwBPYydYj6OHOAmKQF3x03Hmt6Se2F.mCRjVCpmsga', NULL, '2024-04-24 16:48:08', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 362 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户_角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (175, 130, 1);
INSERT INTO `user_role` VALUES (246, 3, 1);
INSERT INTO `user_role` VALUES (343, 163, 1);
INSERT INTO `user_role` VALUES (351, 111, 1);
INSERT INTO `user_role` VALUES (352, 158, 1);
INSERT INTO `user_role` VALUES (353, 1, 5);
INSERT INTO `user_role` VALUES (358, 165, 4);
INSERT INTO `user_role` VALUES (359, 2, 2);
INSERT INTO `user_role` VALUES (360, 151, 3);
INSERT INTO `user_role` VALUES (361, 151, 1);

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

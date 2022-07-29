/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 127.0.0.1:3306
 Source Schema         : soybean-admin

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 15/07/2022 15:17:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for common_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `common_dictionary`;
CREATE TABLE `common_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(64) NOT NULL COMMENT '编码\r\n一颗树仅仅有一个统一的编码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `readonly` bit(1) DEFAULT b'0' COMMENT '内置角色',
  `sequence` tinyint(4) DEFAULT NULL COMMENT '排序',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(50) DEFAULT NULL COMMENT '更新人名称',
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- ----------------------------
-- Records of common_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `common_dictionary` VALUES (1, 'NATION', '民族', '民族', b'1', b'1', 0, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (2, 'POSITION_STATUS', '在职状态', '在职状态', b'1', b'1', 1, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (3, 'EDUCATION', '学历', '学历', b'1', b'1', 2, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (4, 'AREA_LEVEL', '行政区级', '行政区级', b'1', b'1', 3, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (5, 'ORG_TYPE', '机构类型', '机构类型', b'1', b'1', 4, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (6, 'SEX', '性别', '性别', b'1', b'1', 5, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (7, 'NOTICE', '消息类型', '消息类型', b'1', b'1', 6, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (8, 'STATION_TYPE', '岗位类型', '岗位类型', b'1', b'1', 7, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (9, 'COLOR', '颜色', '颜色', b'1', b'1', 8, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary` VALUES (10, 'INDUSTRY', '行业类型', '行业类型', b'1', b'1', 9, 1, '系统管理员', '2022-07-08 00:00:00', 1, '系统管理员', '2022-07-08 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for common_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `common_dictionary_item`;
CREATE TABLE `common_dictionary_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dictionary_id` bigint(20) NOT NULL COMMENT '类型ID',
  `dictionary_code` varchar(64) NOT NULL COMMENT '类型',
  `value` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
  `label` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `sequence` int(11) DEFAULT '1' COMMENT '排序',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(255) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `dict_code_item_code_uniq` (`dictionary_code`,`value`) USING BTREE COMMENT '字典编码与字典项目编码联合唯一'
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Records of common_dictionary_item
-- ----------------------------
BEGIN;
INSERT INTO `common_dictionary_item` VALUES (1, 4, 'AREA_LEVEL', 'COUNTRY', '国家', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (2, 4, 'AREA_LEVEL', 'PROVINCE', '省份', b'1', 'warning', '', 2, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (3, 4, 'AREA_LEVEL', 'CITY', '地市', b'1', 'warning', '', 3, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (4, 4, 'AREA_LEVEL', 'COUNTY', '区县', b'1', 'warning', '', 4, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (5, 5, 'ORG_TYPE', '01', '单位', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (6, 5, 'ORG_TYPE', '02', '部门', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (38, 3, 'EDUCATION', 'ZHUANKE', '专科', b'1', 'warning', '', 4, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (39, 3, 'EDUCATION', 'COLLEGE', '本科', b'1', 'warning', '', 5, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (40, 3, 'EDUCATION', 'SUOSHI', '硕士', b'1', 'warning', '', 6, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (41, 3, 'EDUCATION', 'BOSHI', '博士', b'1', 'warning', '', 7, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (42, 3, 'EDUCATION', 'BOSHIHOU', '博士后', b'1', 'warning', '', 8, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (43, 1, 'NATION', 'mz_hanz', '汉族', b'1', 'warning', '', 0, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (44, 1, 'NATION', 'mz_zz', '壮族', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (45, 1, 'NATION', 'mz_mz', '满族', b'1', 'warning', '', 2, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (46, 1, 'NATION', 'mz_hz', '回族', b'1', 'warning', '', 3, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (47, 1, 'NATION', 'mz_miaoz', '苗族', b'1', 'warning', '', 4, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (48, 1, 'NATION', 'mz_wwez', '维吾尔族', b'1', 'warning', '', 5, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (49, 1, 'NATION', 'mz_tjz', '土家族', b'1', 'warning', '', 6, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (50, 1, 'NATION', 'mz_yz', '彝族', b'1', 'warning', '', 7, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (51, 1, 'NATION', 'mz_mgz', '蒙古族', b'1', 'warning', '', 8, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (52, 1, 'NATION', 'mz_zhangz', '藏族', b'1', 'warning', '', 9, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (53, 1, 'NATION', 'mz_byz', '布依族', b'1', 'warning', '', 10, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (54, 1, 'NATION', 'mz_dz', '侗族', b'1', 'warning', '', 11, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (55, 1, 'NATION', 'mz_yaoz', '瑶族', b'1', 'warning', '', 12, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (56, 1, 'NATION', 'mz_cxz', '朝鲜族', b'1', 'warning', '', 13, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (57, 1, 'NATION', 'mz_bz', '白族', b'1', 'warning', '', 14, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (58, 1, 'NATION', 'mz_hnz', '哈尼族', b'1', 'warning', '', 15, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (59, 1, 'NATION', 'mz_hskz', '哈萨克族', b'1', 'warning', '', 16, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (60, 1, 'NATION', 'mz_lz', '黎族', b'1', 'warning', '', 17, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (61, 1, 'NATION', 'mz_daiz', '傣族', b'1', 'warning', '', 18, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (62, 1, 'NATION', 'mz_sz', '畲族', b'1', 'warning', '', 19, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (63, 1, 'NATION', 'mz_llz', '傈僳族', b'1', 'warning', '', 20, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (64, 1, 'NATION', 'mz_glz', '仡佬族', b'1', 'warning', '', 21, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (65, 1, 'NATION', 'mz_dxz', '东乡族', b'1', 'warning', '', 22, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (66, 1, 'NATION', 'mz_gsz', '高山族', b'1', 'warning', '', 23, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (67, 1, 'NATION', 'mz_lhz', '拉祜族', b'1', 'warning', '', 24, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (68, 1, 'NATION', 'mz_shuiz', '水族', b'1', 'warning', '', 25, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (69, 1, 'NATION', 'mz_wz', '佤族', b'1', 'warning', '', 26, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (70, 1, 'NATION', 'mz_nxz', '纳西族', b'1', 'warning', '', 27, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (71, 1, 'NATION', 'mz_qz', '羌族', b'1', 'warning', '', 28, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (72, 1, 'NATION', 'mz_tz', '土族', b'1', 'warning', '', 29, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (73, 1, 'NATION', 'mz_zlz', '仫佬族', b'1', 'warning', '', 30, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (74, 1, 'NATION', 'mz_xbz', '锡伯族', b'1', 'warning', '', 31, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (75, 1, 'NATION', 'mz_kehzz', '柯尔克孜族', b'1', 'warning', '', 32, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (76, 1, 'NATION', 'mz_dwz', '达斡尔族', b'1', 'warning', '', 33, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (77, 1, 'NATION', 'mz_jpz', '景颇族', b'1', 'warning', '', 34, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (78, 1, 'NATION', 'mz_mlz', '毛南族', b'1', 'warning', '', 35, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (79, 1, 'NATION', 'mz_slz', '撒拉族', b'1', 'warning', '', 36, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (80, 1, 'NATION', 'mz_tjkz', '塔吉克族', b'1', 'warning', '', 37, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (81, 1, 'NATION', 'mz_acz', '阿昌族', b'1', 'warning', '', 38, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (82, 1, 'NATION', 'mz_pmz', '普米族', b'1', 'warning', '', 39, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (83, 1, 'NATION', 'mz_ewkz', '鄂温克族', b'1', 'warning', '', 40, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (84, 1, 'NATION', 'mz_nz', '怒族', b'1', 'warning', '', 41, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (85, 1, 'NATION', 'mz_jz', '京族', b'1', 'warning', '', 42, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (86, 1, 'NATION', 'mz_jnz', '基诺族', b'1', 'warning', '', 43, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (87, 1, 'NATION', 'mz_daz', '德昂族', b'1', 'warning', '', 44, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (88, 1, 'NATION', 'mz_baz', '保安族', b'1', 'warning', '', 45, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (89, 1, 'NATION', 'mz_elsz', '俄罗斯族', b'1', 'warning', '', 46, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (90, 1, 'NATION', 'mz_ygz', '裕固族', b'1', 'warning', '', 47, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (91, 1, 'NATION', 'mz_wzbkz', '乌兹别克族', b'1', 'warning', '', 48, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (92, 1, 'NATION', 'mz_mbz', '门巴族', b'1', 'warning', '', 49, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (93, 1, 'NATION', 'mz_elcz', '鄂伦春族', b'1', 'warning', '', 50, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (94, 1, 'NATION', 'mz_dlz', '独龙族', b'1', 'warning', '', 51, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (95, 1, 'NATION', 'mz_tkez', '塔塔尔族', b'1', 'warning', '', 52, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (96, 1, 'NATION', 'mz_hzz', '赫哲族', b'1', 'warning', '', 53, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (97, 1, 'NATION', 'mz_lbz', '珞巴族', b'1', 'warning', '', 54, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (98, 1, 'NATION', 'mz_blz', '布朗族', b'1', 'warning', '', 55, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (99, 2, 'POSITION_STATUS', 'WORKING', '在职', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (100, 2, 'POSITION_STATUS', 'QUIT', '离职', b'1', 'warning', '', 2, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (101, 4, 'AREA_LEVEL', 'TOWNS', '乡镇', b'1', 'warning', '', 5, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (102, 3, 'EDUCATION', 'XIAOXUE', '小学', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (103, 3, 'EDUCATION', 'ZHONGXUE', '中学', b'1', 'warning', '', 2, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (104, 3, 'EDUCATION', 'GAOZHONG', '高中', b'1', 'warning', '', 3, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (105, 3, 'EDUCATION', 'QITA', '其他', b'1', 'warning', '', 20, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (106, 1, 'NATION', 'mz_qt', '其他', b'1', 'warning', '', 100, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (107, 2, 'POSITION_STATUS', 'LEAVE', '请假', b'1', 'warning', '', 3, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (108, 6, 'SEX', '1', '男', b'1', 'success', '男', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (109, 6, 'SEX', '2', '女', b'1', 'error', '女', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (110, 7, 'NOTICE', '0', '通知', b'1', 'success', '通知', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (111, 7, 'NOTICE', '1', '消息', b'1', 'success', '消息', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (112, 7, 'NOTICE', '2', '待办', b'1', 'error', '待办', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (113, 8, 'STATION_TYPE', '0', '基层', b'1', 'success', '1111', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (114, 9, 'COLOR', 'success', '成功', b'1', 'success', '成功', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (115, 9, 'COLOR', 'warning', '警告', b'1', 'warning', '警告', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (116, 9, 'COLOR', 'error', '错误', b'1', 'error', '红色', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (118, 8, 'STATION_TYPE', '1', '中层', b'1', 'success', '中层', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (119, 8, 'STATION_TYPE', '2', '高层', b'1', 'warning', '高层', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (120, 10, 'INDUSTRY', '1', '医疗', b'1', 'success', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, '1', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (121, 10, 'INDUSTRY', '2', '教育', b'1', 'success', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, '1', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (122, 10, 'INDUSTRY', '3', '金融', b'1', 'success', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, '1', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (123, 10, 'INDUSTRY', '4', '互联网', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (124, 10, 'INDUSTRY', '5', '电商', b'1', 'warning', '', 1, 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `common_dictionary_item` VALUES (125, 9, 'COLOR', 'info', '信息', b'1', 'info', '信息', 1, 1, 'admin', '2022-07-10 22:07:29', NULL, NULL, '2022-07-10 22:12:27');
COMMIT;

-- ----------------------------
-- Table structure for common_login_log
-- ----------------------------
DROP TABLE IF EXISTS `common_login_log`;
CREATE TABLE `common_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '登录人ID',
  `ip` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录IP',
  `client_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录人客户端ID',
  `name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录人姓名',
  `principal` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录人账号',
  `platform` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '平台',
  `engine` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '引擎类型',
  `engine_version` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '引擎版本',
  `browser` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '浏览器版本',
  `os` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '操作系统',
  `location` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录地点',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Table structure for common_opt_log
-- ----------------------------
DROP TABLE IF EXISTS `common_opt_log`;
CREATE TABLE `common_opt_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL COMMENT '操作IP',
  `location` varchar(255) DEFAULT NULL,
  `trace` varchar(255) DEFAULT NULL COMMENT '日志链路追踪id日志标志',
  `type` varchar(5) DEFAULT 'OPT' COMMENT '日志类型\n#LogType{OPT:操作类型;EX:异常类型}',
  `description` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `class_path` varchar(255) DEFAULT NULL COMMENT '类路径',
  `action_method` varchar(50) DEFAULT NULL COMMENT '请求方法',
  `request_uri` varchar(50) DEFAULT NULL COMMENT '请求地址',
  `http_method` varchar(10) DEFAULT 'GET' COMMENT '请求类型\n#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
  `params` longtext COMMENT '请求参数',
  `result` longtext COMMENT '返回值',
  `ex_desc` longtext COMMENT '异常详情信息',
  `ex_detail` longtext COMMENT '异常描述',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `consuming_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  `browser` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `os` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `engine` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `engine_version` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `platform` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `browser_version` varchar(500) DEFAULT NULL COMMENT '浏览器',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(50) DEFAULT NULL COMMENT '操作人',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `sys_oauth_client_details` VALUES ('client', 'client', b'1', 0, NULL, '客户端', 'server', 'password,client_credentials,authorization_code', NULL, NULL, 86400, 604800, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `label` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `alias` varchar(255) DEFAULT '' COMMENT '简称',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `tree_path` varchar(255) DEFAULT ',' COMMENT '所有父级ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `sequence` int(11) DEFAULT '1' COMMENT '排序',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(50) DEFAULT NULL COMMENT '更新人名称',
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10211 DEFAULT CHARSET=utf8mb4 COMMENT='组织';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES (100, '总公司', 1, '总公司', '18500000000', ',', 0, 1, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10001, '北京分公司', 1, '北京分公司', NULL, ',100', 100, 0, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10002, '上海分公司', 1, '上海分公司', NULL, ',100', 100, 1, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10003, '总经办', 1, '总经办', NULL, ',100', 100, 0, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10004, '运营部', 1, '运营部', NULL, ',100', 100, 1, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10005, '产品部', 1, '产品部', NULL, ',100', 100, 2, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10006, '研发部', 1, '研发部', NULL, ',100', 100, 3, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10101, '上海-销售部', 1, '上海-销售部', NULL, ',100,10001', 10001, 0, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10102, '上海-运营部', 1, '上海-运营部', NULL, ',100,10001', 10001, 1, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10103, '上海-产品部', 1, '上海-产品部', NULL, ',100,10001', 10001, 2, b'0', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10201, '北京-销售部', 1, '北京-销售部', NULL, ',100,10002', 10002, 0, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10202, '北京-运营部', 1, '北京-运营部', NULL, ',100,10002', 10002, 1, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10203, '北京-研发部', 1, '北京-研发部', NULL, ',100,10002', 10002, 2, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
INSERT INTO `sys_org` VALUES (10204, '北京-测试部', 1, '北京-测试部', NULL, ',100,10002', 10002, 3, b'1', '初始化数据', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `tree_path` varchar(500) DEFAULT ',' COMMENT '该节点的所有父节点',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级菜单ID',
  `path` varchar(255) DEFAULT '' COMMENT '路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `sequence` int(11) DEFAULT '1' COMMENT '排序',
  `icon` varchar(255) DEFAULT '' COMMENT '菜单图标',
  `style` varchar(255) DEFAULT NULL COMMENT '样式',
  `type` tinyint(4) DEFAULT '1' COMMENT '类型（1=菜单;2=按钮）',
  `status` bit(1) DEFAULT b'1' COMMENT '1=启用;0=禁用',
  `readonly` bit(1) DEFAULT b'0' COMMENT '内置菜单（0=否;1=是）',
  `global` bit(1) DEFAULT b'0' COMMENT '公共资源\nTrue是无需分配所有人就可以访问的',
  `display` bit(1) DEFAULT b'1' COMMENT '0=隐藏;1=显示',
  `description` varchar(200) DEFAULT '' COMMENT '描述',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `created_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `last_modified_name` varchar(255) DEFAULT NULL COMMENT '更新人名称',
  `last_modified_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INX_STATUS` (`global`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1030104 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES (1, '系统管理', ',', 'system:view', 0, '/system', 'LAYOUT', 1, 'SettingFilled', NULL, 1, b'1', b'0', b'0', b'1', '系统管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (2, '租户中心', ',', 'tenant:view', 0, '/tenant', 'LAYOUT', 2, 'ClusterOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (3, '开发平台', ',', 'development:view', 0, '/development', 'LAYOUT', 3, 'CloudServerOutlined', NULL, 1, b'1', b'0', b'0', b'1', '运维监控', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (101, '用户中心', ',1', 'user:info:view', 1, '/system/center', NULL, 0, 'UserOutlined', NULL, 1, b'1', b'0', b'0', b'1', '用户中心', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (102, '权限管理', ',1', 'auth:view', 1, '/system/auth', NULL, 1, 'FileProtectOutlined', NULL, 1, b'1', b'0', b'0', b'1', '权限管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (103, '基础数据', ',1', 'basic:view', 1, '/system/basic', NULL, 2, 'DatabaseFilled', NULL, 1, b'1', b'0', b'0', b'1', '基础配置', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (104, '日志管理', ',1', 'log:view', 1, '/system/log', NULL, 3, 'FileTextFilled', NULL, 1, b'1', b'0', b'0', b'1', '日志管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (201, '租户管理', ',2', 'tenant:management', 2, '/tenant/manage', '/platform/tenant/manage/index', 0, 'BuildFilled', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (202, '连接管理', ',2', NULL, 2, '/tenant/database', '/platform/tenant/database/index', 1, 'ApiFilled', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (5001, '网关管理', ',3', 'system:gateway:view', 3, '/development/gateway', NULL, 1, 'GatewayOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (5002, '应用管理', ',3', 'application:management', 3, '/system/application', '/platform/system/application/index', 2, 'AppstoreOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (5003, '监控中心', ',3', 'development:view', 3, '/development/monitor', 'http://localhost:10000/monitor', 3, 'MonitorOutlined', NULL, 1, b'1', b'0', b'0', b'1', '监控中心', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (5004, '注册中心', ',3', 'service:governance:nacos:view', 3, '/development/nacos', 'http://127.0.0.1:30870/nacos', 4, 'PicCenterOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10101, '用户管理', ',1,101', 'user:management', 101, '/system/center/user', '/platform/system/center/user/index', 0, 'UserProfile', NULL, 1, b'1', b'0', b'0', b'1', '用户管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10102, '组织管理', ',1,101', 'org:view', 101, '/system/center/org', '/platform/system/center/org/index', 1, 'ApartmentOutlined', NULL, 1, b'1', b'0', b'0', b'1', '组织管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10103, '岗位管理', ',1,101', 'station:management', 101, '/system/center/station', '/platform/system/center/station/index', 2, 'Adjustments', NULL, 1, b'1', b'0', b'0', b'1', '岗位管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10201, '菜单管理', ',1,102', 'menu:view', 102, '/system/auth/menu', '/platform/system/auth/menu/index', 0, 'Menu', NULL, 1, b'1', b'0', b'0', b'1', '菜单管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10202, '角色管理', ',1,102', 'role:management', 102, '/system/auth/role', '/platform/system/auth/role/index', 1, 'UserRole', NULL, 1, b'1', b'0', b'0', b'1', '角色管理', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10301, '数据字典', ',1,103', 'dict:management', 103, '/system/basic/dictionary', '/platform/system/basic/dictionary/index', 0, 'FileCode', NULL, 1, b'1', b'0', b'0', b'1', '数据字典', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10302, '地区信息', ',1,103', 'area:view', 103, '/system/basic/area', '/platform/system/basic/area/index', 1, 'Map', NULL, 1, b'1', b'0', b'0', b'1', '地区信息', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10401, '登录日志', ',1,104', 'log:login', 104, '/system/log/login', '/platform/system/log/login/index', 0, 'LogIn', NULL, 1, b'1', b'0', b'0', b'1', '登录日志', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (10402, '操作日志', ',1,104', 'log:opt', 104, '/system/log/opt', '/platform/system/log/opt/index', 1, 'OperationsRecord', NULL, 1, b'1', b'0', b'0', b'1', '操作日志', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (500201, '限流规则', ',50,5001', 'development:gateway:limit', 5001, '/development/gateway/limit', '/platform/development/gateway/limit/index', 1, 'FileTextOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (500202, '限访名单', ',50,5001', 'development:gateway:blacklist', 5001, '/development/gateway/blacklist', '/platform/development/gateway/blacklist/index', 2, 'LockOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (500203, '网关路由', ',50,5001', 'development:gateway:route', 5001, '/development/gateway/route', '/platform/development/gateway/route/index', 3, 'BorderOuterOutlined', NULL, 1, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010101, '添加', ',1,101,10101', 'user:management:add', 10101, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010102, '编辑', ',1,101,10101', 'user:management:edit', 10101, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010104, '删除', ',1,101,10101', 'user:management:remove', 10101, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010301, '添加', ',1,101,10103', 'station:management:add', 10103, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010302, '编辑', ',1,101,10103', 'station:management:edit', 10103, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1010303, '删除', ',1,101,10103', 'station:management:remove', 10103, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1020201, '添加', ',1,102,10202', 'role:management:add', 10202, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1020202, '编辑', ',1,102,10202', 'role:management:edit', 10202, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1020203, '删除', ',1,102,10202', 'role:management:remove', 10202, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1020204, '分配用户', ',1,102,10202', 'role:management:distribution_user', 10202, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1020205, '分配权限', ',1,102,10202', 'role:management:distribution_res', 10202, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1030101, '添加', ',1,103,10301', 'dict:management:add', 10301, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1030102, '编辑', ',1,103,10301', 'dict:management:edit', 10301, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
INSERT INTO `sys_resource` VALUES (1030103, '删除', ',1,103,10301', 'dict:management:remove', 10301, '', NULL, 1, '', NULL, 2, b'1', b'0', b'0', b'1', '', 1, 'admin', '2022-07-08 00:00:00', 1, 'admin', '2022-07-08 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户编码',
  `code` varchar(30) DEFAULT NULL COMMENT '角色编码',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  `scope_type` tinyint(4) DEFAULT NULL COMMENT '数据权限范围，值越大，权限越大',
  `locked` tinyint(1) DEFAULT '0' COMMENT '0=正常1=禁用',
  `super` tinyint(1) DEFAULT '0' COMMENT '0=非 1=管理员',
  `readonly` tinyint(1) DEFAULT '0' COMMENT '是否内置角色',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(255) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 1, 'PLATFORM_ADMIN', '平台管理员', '拥有所有权限', 50, 0, 1, 1, 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID',
  UNIQUE KEY `role_id` (`role_id`,`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `res_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `idx_role_res` (`role_id`,`res_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_res` VALUES (1, 1, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 2, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 3, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 101, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 102, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 103, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 104, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 201, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 202, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 5001, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 5002, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 5003, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 5004, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10101, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10102, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10103, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10201, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10202, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10301, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10302, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10401, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 10402, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 500201, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 500202, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 500203, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010101, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010102, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010104, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010301, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010302, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1010303, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1020201, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1020202, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1020203, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1020204, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1020205, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1030101, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1030102, '2022-07-08 00:00:00');
INSERT INTO `sys_role_res` VALUES (1, 1030103, '2022-07-08 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_station
-- ----------------------------
DROP TABLE IF EXISTS `sys_station`;
CREATE TABLE `sys_station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `sequence` tinyint(4) DEFAULT NULL COMMENT '排序',
  `org_id` bigint(20) DEFAULT '0' COMMENT '组织ID\n#c_core_org',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `created_by` bigint(20) DEFAULT NULL,
  `created_name` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_name` varchar(255) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COMMENT='岗位';

-- ----------------------------
-- Records of sys_station
-- ----------------------------
BEGIN;
INSERT INTO `sys_station` VALUES (100, 1, '总经理', 'CEO', 2, 0, 100, b'1', '总公司-1把手', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  UNIQUE KEY `role_id` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `content_type` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `location` varchar(50) DEFAULT NULL COMMENT '登录地点',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  `engine` varchar(255) DEFAULT NULL COMMENT '引擎类型',
  `engine_version` varchar(255) DEFAULT NULL COMMENT '引擎版本',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `bucket` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `origin_name` varchar(255) DEFAULT NULL COMMENT '原始名称',
  `target_name` varchar(255) DEFAULT NULL COMMENT '目标名称',
  `mapping_path` varchar(255) DEFAULT NULL COMMENT '映射地址',
  `full_url` varchar(255) DEFAULT NULL COMMENT '完整地址',
  `extend` varchar(255) DEFAULT NULL COMMENT '拓展字段',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(50) DEFAULT NULL COMMENT '更新人名称',
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_TARGET_NAME` (`target_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `username` varchar(30) NOT NULL COMMENT '账号',
  `password` varchar(200) DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织ID\n#c_core_org\n@InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, com.github.zuihou.authority.entity.core.Org>',
  `station_id` bigint(20) DEFAULT NULL COMMENT '岗位ID\n#c_core_station\n@InjectionField(api = STATION_ID_CLASS, method = STATION_ID_NAME_METHOD) RemoteData<Long, String>',
  `readonly` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否内置',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证',
  `sex` tinyint(4) DEFAULT '1' COMMENT '性别\n#Sex{W:女;M:男;N:未知}',
  `status` bit(1) DEFAULT b'0' COMMENT '状态 \n1启用 0禁用',
  `avatar` varchar(510) DEFAULT '' COMMENT '头像',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.NATION) RemoteData<String, String>\n',
  `education` varchar(20) DEFAULT NULL COMMENT '学历\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.EDUCATION) RemoteData<String, String>',
  `position_status` varchar(20) DEFAULT NULL COMMENT '职位状态\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.POSITION_STATUS) RemoteData<String, String>',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `created_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `last_modified_name` varchar(50) DEFAULT NULL COMMENT '更新人名称',
  `last_modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_ACCOUNT_TENANT` (`username`,`tenant_id`) USING BTREE COMMENT '账号唯一约束'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='租户配置信息';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, ${tenant_id}, 'admin', '{bcrypt}$2a$10$R2AdNVf402GnqcJejdjY..wOHP5hFt5x0vz5qXdTVG.udcdFmqu.K', 'admin', 100, 100, b'1', '1571339199@qq.com', '18500000000', '000000111122223333', 1, b'1', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202105%2F29%2F20210529001057_aSeLB.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659524668&t=c7c3d14f29d34bee78e016cb868aede3', 'admin', 'mz_hanz', 'COLLEGE', 'WORKING', '1970-01-01', 1, 'admin', '2022-07-01 00:00:00', 1, 'admin', '2022-07-10 23:49:59');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

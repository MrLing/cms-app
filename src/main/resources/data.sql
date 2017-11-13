/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : test

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 07/01/2017 00:07:08 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `crm_member`
-- ----------------------------
DROP TABLE IF EXISTS `crm_member`;
CREATE TABLE `crm_member` (
  `id`        bigint(20) NOT NULL AUTO_INCREMENT,
  `password`  varchar(128) NOT NULL,
  `user_name` varchar(64) NOT NULL,
  `status`    bit(1)              DEFAULT NULL,
  `email`     VARCHAR(64)         DEFAULT NULL,
  `gender`    VARCHAR(255)        DEFAULT NULL,
  `hiredate`  DATETIME            DEFAULT NULL,
  `real_name` VARCHAR(64) NOT NULL,
  `telephone` VARCHAR(64)         DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3w4x463xehrckku45kvs911ml` (`user_name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 36
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Records of `crm_member`
-- ----------------------------
BEGIN;
INSERT INTO `crm_member` VALUES ('1', '9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0', 'admin', b'1', '768870379@qq.com', 'GIRL', '2017-06-30 00:00:00', '管理员', '18676037292'), ('31', '9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0', 'gson', b'0', 'wmails@126.com', 'BOY', '2017-05-08 00:00:00', '郭华', '13203314875');
COMMIT;

-- ----------------------------
--  Table structure for `crm_member_role`
-- ----------------------------
DROP TABLE IF EXISTS `crm_member_role`;
CREATE TABLE `crm_member_role` (
  `member_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKb17jj8ou6rp2lkxb5xen5tixe` (`role_id`),
  KEY `FK76a8mc5mub4tu1gndxph4ypls` (`member_id`),
  CONSTRAINT `FK76a8mc5mub4tu1gndxph4ypls` FOREIGN KEY (`member_id`) REFERENCES `crm_member` (`id`),
  CONSTRAINT `FKb17jj8ou6rp2lkxb5xen5tixe` FOREIGN KEY (`role_id`) REFERENCES `crm_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `crm_resource`
-- ----------------------------
DROP TABLE IF EXISTS `crm_resource`;
CREATE TABLE `crm_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fun_urls` varchar(1024) DEFAULT NULL,
  `menu_url` varchar(128) DEFAULT NULL,
  `res_key` varchar(128) NOT NULL,
  `res_name` varchar(128) NOT NULL,
  `res_type` varchar(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ic22mdco0hjpt8qjosdnyhxcx` (`res_key`),
  KEY `FKo4megp72bdlng5bpjmo56v1wk` (`parent_id`),
  CONSTRAINT `FKo4megp72bdlng5bpjmo56v1wk` FOREIGN KEY (`parent_id`) REFERENCES `crm_resource` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Records of `crm_resource`
-- ----------------------------
BEGIN;
INSERT INTO `crm_resource` VALUES ('1', '', '', 'system', '系统管理', 'MENU', b'1', NULL, '0'), ('3', '/system/member/list', '/system/member', 'system-member', '用户管理', 'MENU', b'1', '1', NULL), ('10', '/system/role/list,/system/role/resource/tree', '/system/role', 'system-role', '角色管理', 'MENU', b'1', '1', NULL), ('11', '/system/resource/list', '/system/resource', 'system-resource', '资源管理', 'MENU', b'1', '1', NULL), ('12', '', '', 'role-create', '创建角色', 'FUNCTION', b'1', '10', NULL), ('13', '', '/system/role/delete', 'role-delete', '删除角色', 'FUNCTION', b'1', '10', NULL), ('14', '/system/role/update,/system/role/save', '', 'role-save', '保存编辑', 'FUNCTION', b'1', '10', NULL), ('17', '/system/role/resource/save', '', 'reole-resource-save', '分配资源', 'FUNCTION', b'1', '10', NULL), ('18', '/system/resource/form,/system/resource/parent/tree,/system/resource/save', '', 'resource-create', '创建资源', 'FUNCTION', b'1', '11', NULL), ('19', '/system/resource/form,/system/resource/parent/tree,/system/resource/save', '', 'resource-edit', '编辑', 'FUNCTION', b'1', '11', NULL), ('20', '/system/resource/delete', '', 'resource-delete', '删除', 'FUNCTION', b'1', '11', NULL), ('21', '/system/member/form,/system/member/save', '', 'member-create', '创建用户', 'FUNCTION', b'1', '3', NULL), ('22', '/system/member/delete', '', 'member-delete', '删除用户', 'FUNCTION', b'1', '3', NULL), ('23', '/system/member/form,/system/member/update', '', 'member-edit', '编辑用户', 'FUNCTION', b'1', '3', NULL), ('26', '/system/member/password/reset', '', 'member-reset-password', '重置密码', 'FUNCTION', b'1', '3', NULL);
COMMIT;

-- ----------------------------
--  Table structure for `crm_role`
-- ----------------------------
DROP TABLE IF EXISTS `crm_role`;
CREATE TABLE `crm_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `role_name` varchar(30) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r0jsnwb00o0n376ghyuahuqfg` (`role_name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Records of `crm_role`
-- ----------------------------
BEGIN;
INSERT INTO `crm_role` VALUES ('1', '有系统所有权限', '管理员', b'1'), ('2', '主要是上课，可以查看学员管理模块', '教员', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `crm_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `crm_role_resource`;
CREATE TABLE `crm_role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  KEY `FKjwyt61kixx52wper9y0li38c2` (`resource_id`),
  KEY `FKasi3s87a7p562cyw0jt3m0isf` (`role_id`),
  CONSTRAINT `FKasi3s87a7p562cyw0jt3m0isf` FOREIGN KEY (`role_id`) REFERENCES `crm_role` (`id`),
  CONSTRAINT `FKjwyt61kixx52wper9y0li38c2` FOREIGN KEY (`resource_id`) REFERENCES `crm_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `crm_role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `crm_role_resource` VALUES ('1', '1'), ('1', '3'), ('1', '21'), ('1', '22'), ('1', '23'), ('1', '10'), ('1', '12'), ('1', '13'), ('1', '14'), ('1', '17'), ('1', '11'), ('1', '18'), ('1', '19'), ('1', '20');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

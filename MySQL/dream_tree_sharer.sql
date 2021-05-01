/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : dream_tree_sharer

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 01/05/2021 12:14:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `admin_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名',
  `admin_password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登录密码',
  `admin_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建管理员时间',
  `admin_login_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '管理员最新登录时间',
  `admin_enabled` tinyint(1) NULL DEFAULT NULL COMMENT '管理员是否被启用',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pinboards
-- ----------------------------
DROP TABLE IF EXISTS `pinboards`;
CREATE TABLE `pinboards`  (
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `pinboard_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板名',
  `pinboard_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板内容',
  `pinboard_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建愿望板时间',
  `pinboard_bgimg_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '愿望板背景图地址',
  `pinboard_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板类型',
  PRIMARY KEY (`pinboard_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '愿望板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录密码',
  `user_sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `user_birthday` date NULL DEFAULT NULL COMMENT '用户生日',
  `user_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户自定义描述',
  `user_avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' COMMENT '用户头像地址',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建用户时间',
  `user_login_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户最新登录时间',
  `user_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '用户是否被禁用 1-表示没禁用 0-表示被禁用',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('02abc87a-34a3-49b0-85b1-2bd9b0c818e5', 'summer', '11ce4cf157aeb5665b770c670dc6e219', NULL, NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '15244812873', NULL, '2021-04-24 19:47:32', '2021-04-24 19:47:32', 1);
INSERT INTO `users` VALUES ('70a23a89-adc4-4738-a15a-a89ce50dff7f', 'summerlvlv', 'WWW_dts123', NULL, NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '15244812873', NULL, '2021-04-25 18:14:41', '2021-04-25 18:14:41', 1);
INSERT INTO `users` VALUES ('f31a1b69-c3ac-448a-aca8-e546f56072bb', 'summerlv', '11ce4cf157aeb5665b770c670dc6e219', NULL, NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '15244812873', NULL, '2021-04-24 11:04:10', '2021-04-24 11:04:10', 1);

-- ----------------------------
-- Table structure for users_pinboards
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards`;
CREATE TABLE `users_pinboards`  (
  `up_id` int(11) NOT NULL COMMENT '用户-愿望板id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `up_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建愿望板时间',
  PRIMARY KEY (`up_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `pinboard_id`(`pinboard_id`) USING BTREE,
  CONSTRAINT `users_pinboards_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_pinboards_ibfk_2` FOREIGN KEY (`pinboard_id`) REFERENCES `pinboards` (`pinboard_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对愿望板的操作记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users_pinboards_comments
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards_comments`;
CREATE TABLE `users_pinboards_comments`  (
  `upc_id` int(11) NOT NULL COMMENT '用户-愿望板-评论id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `up_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建愿望板时间',
  PRIMARY KEY (`upc_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `pinboard_id`(`pinboard_id`) USING BTREE,
  CONSTRAINT `users_pinboards_comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_pinboards_comments_ibfk_2` FOREIGN KEY (`pinboard_id`) REFERENCES `pinboards` (`pinboard_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对愿望板的评论记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users_pinboards_favorites
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards_favorites`;
CREATE TABLE `users_pinboards_favorites`  (
  `upf_id` int(11) NOT NULL COMMENT '用户-愿望板-收藏id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `up_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建愿望板时间',
  PRIMARY KEY (`upf_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `pinboard_id`(`pinboard_id`) USING BTREE,
  CONSTRAINT `users_pinboards_favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_pinboards_favorites_ibfk_2` FOREIGN KEY (`pinboard_id`) REFERENCES `pinboards` (`pinboard_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对愿望板的收藏记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users_pinboards_likes
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards_likes`;
CREATE TABLE `users_pinboards_likes`  (
  `upl_id` int(11) NOT NULL COMMENT '用户-愿望板-点赞id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `up_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`upl_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `pinboard_id`(`pinboard_id`) USING BTREE,
  CONSTRAINT `users_pinboards_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_pinboards_likes_ibfk_2` FOREIGN KEY (`pinboard_id`) REFERENCES `pinboards` (`pinboard_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对愿望板的点赞记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

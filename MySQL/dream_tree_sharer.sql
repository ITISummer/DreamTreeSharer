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

 Date: 11/05/2021 20:35:43
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
  `pinboard_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '愿望板类型',
  `pinboard_sharable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否分享愿望板',
  `like_num` int(11) NULL DEFAULT NULL COMMENT '愿望板点赞量',
  PRIMARY KEY (`pinboard_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '愿望板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pinboards
-- ----------------------------
INSERT INTO `pinboards` VALUES ('1f0979e8-59fa-4b21-8350-f37a697746c3', 'what？', 'why？', '2021-05-08 21:27:42', 'http://qrne6et6u.hn-bkt.clouddn.com/IMG_20200606_231803.jpg', NULL, 1, NULL);
INSERT INTO `pinboards` VALUES ('5984ed53-3510-49d3-b899-03895a4e6bfa', 'heyhey', 'heyhey', '2021-05-09 10:56:06', 'http://qrne6et6u.hn-bkt.clouddn.com/问微信.jpg', NULL, 1, NULL);
INSERT INTO `pinboards` VALUES ('6d367175-2613-4eb2-a5c8-bf9bfca6570d', 'what？', 'why？', '2021-05-08 21:27:42', 'http://qrne6et6u.hn-bkt.clouddn.com/My Dream.jpg', NULL, 1, NULL);
INSERT INTO `pinboards` VALUES ('87445213-adc9-48bf-9d7e-dd44add1fedb', 'What is your dream?', 'My dream is to be a good programmer!', '2021-05-08 20:35:13', 'http://qrne6et6u.hn-bkt.clouddn.com/WeChat Image_20200607175036.jpg', NULL, 1, NULL);

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
INSERT INTO `users` VALUES ('02abc87a-34a3-49b0-85b1-2bd9b0c818e5', 'summer', '11ce4cf157aeb5665b770c670dc6e219', NULL, NULL, NULL, 'WeChat Image_20200607175036.jpg', '15244812873', 'lv.summer@qq.com', '2021-04-24 19:47:32', '2021-04-24 19:47:32', 1);
INSERT INTO `users` VALUES ('0560e615-4d16-45b6-9b8f-29ceaa0841d2', 'SummerLyu', '11ce4cf157aeb5665b770c670dc6e219', NULL, NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '15244812873', NULL, '2021-05-02 21:03:01', '2021-05-02 21:03:01', 1);
INSERT INTO `users` VALUES ('70a23a89-adc4-4738-a15a-a89ce50dff7f', 'summerlvlv', '11ce4cf157aeb5665b770c670dc6e219', NULL, NULL, NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '15244812873', NULL, '2021-04-25 18:14:41', '2021-04-25 18:14:41', 1);
INSERT INTO `users` VALUES ('f31a1b69-c3ac-448a-aca8-e546f56072bb', 'summerlv', '605212848ed0bb0d5cfbd1df7987080f', NULL, NULL, NULL, 'IMG_20200606_231803.jpg', '15244812873', NULL, '2021-04-24 11:04:10', '2021-04-24 11:04:10', 1);

-- ----------------------------
-- Table structure for users_pinboards
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards`;
CREATE TABLE `users_pinboards`  (
  `up_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户-愿望板id',
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
-- Records of users_pinboards
-- ----------------------------
INSERT INTO `users_pinboards` VALUES ('5fd4d14b-7412-4868-81cb-69cf62cd607d', 'f31a1b69-c3ac-448a-aca8-e546f56072bb', '5984ed53-3510-49d3-b899-03895a4e6bfa', '2021-05-09 10:56:06');
INSERT INTO `users_pinboards` VALUES ('966bd6e3-76ce-4c52-87d6-c562ca745606', '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', '2021-05-08 20:35:13');
INSERT INTO `users_pinboards` VALUES ('ca92814b-2a07-4d6b-9eb1-f1c4597b8bd5', '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '6d367175-2613-4eb2-a5c8-bf9bfca6570d', '2021-05-11 09:55:09');
INSERT INTO `users_pinboards` VALUES ('ee534b4d-c098-40f0-beb2-41023eadb013', '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '1f0979e8-59fa-4b21-8350-f37a697746c3', '2021-05-08 21:27:42');

-- ----------------------------
-- Table structure for users_pinboards_comments
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards_comments`;
CREATE TABLE `users_pinboards_comments`  (
  `upc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户-愿望板-评论id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pinboard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '愿望板id',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `up_create_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建愿望板时间',
  `like_num` int(10) NULL DEFAULT NULL COMMENT '评论所收到的点赞数量',
  PRIMARY KEY (`upc_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `pinboard_id`(`pinboard_id`) USING BTREE,
  CONSTRAINT `users_pinboards_comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_pinboards_comments_ibfk_2` FOREIGN KEY (`pinboard_id`) REFERENCES `pinboards` (`pinboard_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对愿望板的评论记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_pinboards_comments
-- ----------------------------
INSERT INTO `users_pinboards_comments` VALUES (2, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', 'hao ge ge', '2021-05-10 13:14:49', 0);
INSERT INTO `users_pinboards_comments` VALUES (3, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', '帆帆帆帆帆帆帆帆', '2021-05-10 13:17:52', 2);
INSERT INTO `users_pinboards_comments` VALUES (4, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', '反反复复发方法', '2021-05-10 13:18:10', 0);
INSERT INTO `users_pinboards_comments` VALUES (5, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', '反反复复', '2021-05-10 13:22:54', 1);
INSERT INTO `users_pinboards_comments` VALUES (6, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', 'what is your dream?', '2021-05-10 15:10:37', 0);
INSERT INTO `users_pinboards_comments` VALUES (7, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', '一个梦想，两个梦想，三个梦想，千万亿个梦想，中国梦，梦之蓝', '2021-05-10 15:27:11', 1);
INSERT INTO `users_pinboards_comments` VALUES (8, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', 'what？', '2021-05-10 15:36:25', 0);
INSERT INTO `users_pinboards_comments` VALUES (9, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '1f0979e8-59fa-4b21-8350-f37a697746c3', '吕长旭，你真棒', '2021-05-10 17:05:15', 0);
INSERT INTO `users_pinboards_comments` VALUES (10, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '87445213-adc9-48bf-9d7e-dd44add1fedb', 'why is it so happy?', '2021-05-10 19:15:45', 0);
INSERT INTO `users_pinboards_comments` VALUES (11, '02abc87a-34a3-49b0-85b1-2bd9b0c818e5', '1f0979e8-59fa-4b21-8350-f37a697746c3', 'heyhey', '2021-05-10 19:16:19', 0);
INSERT INTO `users_pinboards_comments` VALUES (12, 'f31a1b69-c3ac-448a-aca8-e546f56072bb', '87445213-adc9-48bf-9d7e-dd44add1fedb', 'summerlv 的评论', '2021-05-11 10:44:42', 1);

-- ----------------------------
-- Table structure for users_pinboards_favorites
-- ----------------------------
DROP TABLE IF EXISTS `users_pinboards_favorites`;
CREATE TABLE `users_pinboards_favorites`  (
  `upf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户-愿望板-收藏id',
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
-- Records of users_pinboards_favorites
-- ----------------------------
INSERT INTO `users_pinboards_favorites` VALUES (1, 'f31a1b69-c3ac-448a-aca8-e546f56072bb', '87445213-adc9-48bf-9d7e-dd44add1fedb', '2021-05-11 18:45:14');

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

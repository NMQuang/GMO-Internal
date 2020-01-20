/*
 Navicat Premium Data Transfer

 Source Server         : web_internal
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 10.1.10.250:3306
 Source Schema         : db_gzr_webinternal

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 20/01/2020 15:50:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `employee_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` int(1) NOT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `last_logout_date` datetime(0) NULL DEFAULT NULL,
  `last_access_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `employees_id`(`employee_code`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`employee_code`) REFERENCES `employees` (`employee_code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `accounts_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES (1, '$2a$10$IDLWBahF.0IzZ5tW5zydGOX9PU41kp6dZjlfYX3366CgvwB0kpNxG', '2395', 1, 0, '2019-11-25 13:50:46', '2020-01-20 15:40:02', NULL, '2020-01-20 15:40:02');
INSERT INTO `accounts` VALUES (2, '$2a$10$lyoCoNkv/CDmoMEbRoqb9OFUZHCAgqucuvfB1EmwNe9RV8D1Fo2M.', '1827', 4, 0, '2019-11-25 13:50:46', '2020-01-20 15:22:06', '2019-12-25 09:32:26', '2020-01-20 15:22:06');
INSERT INTO `accounts` VALUES (3, '$2a$10$.ZtYMm1CK/WpKWnH7t2ydusdjZzFjoNUe0FtETfG.Rr.J3YIzRwVK', '2875', 3, 0, '2019-11-25 13:50:46', '2020-01-06 10:02:42', NULL, '2019-12-31 16:33:29');
INSERT INTO `accounts` VALUES (4, '$2a$10$eEYv10091mcpts/PIg91.uPTpr0faNLlUDYHcy6bOgEEFmB08W3gu', '1835', 3, 0, '2019-11-25 13:50:46', '2020-01-06 10:11:53', '2019-12-25 09:32:26', '2020-01-06 10:11:53');
INSERT INTO `accounts` VALUES (5, '$2a$10$ago5v4384ZfhWqa0vai1lOXnN42Ghf3jYEn7N.aspnq0FCENEz6Ya', '0001', 1, 0, '2019-11-25 13:50:46', '2020-01-17 08:34:03', NULL, '2020-01-17 08:34:03');
INSERT INTO `accounts` VALUES (6, '$2a$10$NoiwO/ddFO0rBCRADBNqqOGOeIbRphXI0ESDEWeep6t77H2UZKLuq', '0002', 1, 0, '2019-11-25 13:50:46', '2020-01-16 15:04:13', NULL, '2020-01-16 15:04:13');

-- ----------------------------
-- Table structure for check_in_out
-- ----------------------------
DROP TABLE IF EXISTS `check_in_out`;
CREATE TABLE `check_in_out`  (
  `id` int(11) NOT NULL,
  `employee_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `employee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `auto_assign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `on_duty` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `off_duty` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clock_in` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clock_out` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `late` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `early` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exception` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `must_cin` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `must_cout` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `employee_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` int(1) NULL DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `birthplace` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province_pr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `district_pr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ward_pr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nation_ca` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `district_ca` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ward_ca` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `probationary_day` date NOT NULL,
  `official_day` date NOT NULL,
  `work_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type_contract` int(1) NOT NULL,
  `status` int(1) NOT NULL,
  `reason_leave` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `day_off` date NULL DEFAULT NULL,
  `delete_flag` int(1) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `division_id` int(10) NOT NULL,
  `position_id` int(10) NOT NULL,
  `email_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `employee_code`, `email`) USING BTREE,
  INDEX `division_id`(`division_id`) USING BTREE,
  INDEX `position_id`(`position_id`) USING BTREE,
  INDEX `employee_fk1`(`type_contract`) USING BTREE,
  INDEX `employee_fk2`(`status`) USING BTREE,
  INDEX `employee_code`(`employee_code`) USING BTREE,
  CONSTRAINT `employee_fk1` FOREIGN KEY (`type_contract`) REFERENCES `m_type_contact` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `employee_fk2` FOREIGN KEY (`status`) REFERENCES `m_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `employee_fk3` FOREIGN KEY (`division_id`) REFERENCES `m_division` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `employee_fk4` FOREIGN KEY (`position_id`) REFERENCES `m_position` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (1, '2395', 'NGUYỄN MINH QUANG', 'quangnm1@runsystem.net', 1, NULL, '0585313351', '2019-11-11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-11-12', '2019-11-25', 'HCM', 1, 1, NULL, NULL, 0, '2019-11-26 13:46:48', '2019-11-26 13:46:51', 1, 3, '', '', '');
INSERT INTO `employees` VALUES (2, '3096', 'TRÌNH VĂN ĐỒNG', 'hantt@smartgift.vn', 1, '769/48 phạm thế hiển ,p4, q8,hcm', NULL, '1997-11-05', 'Huyện Xuyên Mộc ,Bà Rịa Vũng Tàu', NULL, NULL, NULL, 'Việt Nam', NULL, NULL, '2019-03-04', '2019-07-01', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-11-26 13:47:02', '2019-11-26 13:47:02', 2, 5, '', '', '');
INSERT INTO `employees` VALUES (3, '3191', 'NGUYỄN THỊ THANH HẠ', 'hantt@smartgift.vn', 0, '566 Phường 5, Quận Gò Vấp, thành phố Hồ Chí Minh', NULL, '1996-10-10', 'Đồng Nai', NULL, NULL, NULL, 'Việt Nam', NULL, NULL, '2019-04-17', '2019-06-18', 'Chi Nhánh Hồ Chí Minh', 3, 1, NULL, NULL, 0, '2019-11-26 13:47:02', '2019-11-26 13:47:02', 2, 5, '', '', '');
INSERT INTO `employees` VALUES (4, '1019', 'BÙI QUANG TÂN', 'tanbq@runsystem.net', 1, '340/3 Lê Văn Quới phường Bình Hưng Hoà A Bình Tân', NULL, '1982-10-02', 'Thanh Hoá', NULL, NULL, NULL, NULL, NULL, NULL, '2018-08-10', '2018-11-10', 'HCM', 4, 1, NULL, NULL, 0, '2019-11-26 13:47:02', '2019-11-26 13:47:02', 2, 5, '', '', '');
INSERT INTO `employees` VALUES (5, '1834', 'NGUYỄN THÁI HIỀN', 'hiennt@smartgift.vn', 1, '111/8/2/188 Đặng Thùy Vân (đường Trục), phường 13, quận Bình Thạnh, TP.HCM', NULL, '1991-03-28', 'Bà Rịa - Vũng Tàu', NULL, NULL, NULL, 'Việt Nam', NULL, NULL, '2018-08-10', '2018-11-10', 'Chi Nhánh Hồ Chí Minh', 6, 1, NULL, NULL, 0, '2019-11-26 13:47:02', '2019-11-26 13:47:02', 2, 5, '', '', '');
INSERT INTO `employees` VALUES (6, '0001', 'NGUYỄN TẤT ĐẠT', 'datnt3@runsystem.net', 1, NULL, NULL, '1997-03-28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-04', '2019-12-18', 'HCM', 6, 1, NULL, NULL, 0, '2019-12-04 09:41:51', '2019-12-04 09:41:54', 1, 5, '', '', '');
INSERT INTO `employees` VALUES (7, '0002', 'LÊ MINH TOÀN', 'toanlm@runsystem.net', 1, NULL, NULL, '1997-03-28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-04', '2019-12-18', 'HCM', 3, 1, NULL, NULL, 0, '2019-12-11 09:43:08', '2019-12-12 09:43:11', 1, 5, '', '', '');
INSERT INTO `employees` VALUES (78, '2396', 'QuangNM1', 'minhquangnguyen1996@gmail.com', 1, NULL, NULL, '1997-03-28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-04', '2019-12-18', 'HCM', 3, 1, NULL, NULL, 0, '2019-12-11 09:43:08', '2019-12-12 09:43:11', 1, 5, '', '', '');
INSERT INTO `employees` VALUES (98, '2726', 'NGUYỄN NGỌC QUÍ', 'quinn@runsystem.net', 1, '369, Đào Sư Tích, xã Phước Lộc, huyện Nhà Bè TPHCM', '0919171561', '1988-09-10', 'xã Châu Phong, thị xã Tân Châu, An Giang', NULL, NULL, NULL, NULL, NULL, NULL, '2018-04-23', '2018-06-25', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (99, '1827', 'NGUYỄN HOÀNG HIỆP', 'hiepnh@runsystem.net', 1, '192/2 nguyễn thái bình- Quận Tân Bình', '0974844337', '1989-04-19', 'Đồng Nai', NULL, NULL, NULL, NULL, NULL, NULL, '2014-07-14', '2014-08-14', 'Chi Nhánh Hồ Chí Minh', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (100, '1835', 'NGUYỄN GIẢ HOÀNG VINH', 'vinhngh@runsystem.net', 1, '132/50 Nguyễn Hữu Cảnh, Phường 19, Quận Bình Thạnh', '0986831405', '1986-04-01', 'Khánh Hòa', NULL, NULL, NULL, NULL, NULL, NULL, '2014-07-14', '2014-08-14', 'Chi Nhánh Hồ Chí Minh', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 2, '', '', '');
INSERT INTO `employees` VALUES (101, '1346', 'NGUYỄN PHƯỚC QUANG', 'quangnp@runsystem.net', 1, '511, chung cư 234 Phan Văn Trị, phường 11, quận Bình Thạnh, TP. Hồ Chí Minh', '0909989465', '1984-10-24', 'Gia Lai', NULL, NULL, NULL, NULL, NULL, NULL, '2011-03-28', '2011-05-27', 'HCM', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 2, '', '', '');
INSERT INTO `employees` VALUES (102, '2734', 'NGUYỄN PHÚ AN', 'annp@runsystem.net', 1, 'CC 4s Riverside, Linh Đông, Thủ Đức', '0352732733', '1991-03-10', 'Phú Yên', NULL, NULL, NULL, NULL, NULL, NULL, '2018-05-07', '2018-07-09', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (103, '2533', 'VÕ THÀNH HƯNG', 'hungvt@runsystem.net', 1, '137/3 Nguyễn Lâm, P6, Q10, HCMC', '0909288482', '1990-01-03', 'Tp Hồ Chí Minh', NULL, NULL, NULL, NULL, NULL, NULL, '2017-10-02', '2017-12-01', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (104, '2040', 'TRẦN VĂN SẮT', 'sattv@runsystem.net', 1, 'Khu phố tây, Vĩnh Phú, Thuận An, Bình Dương', '0389802033', '1991-05-10', 'Sóc Trăng', NULL, NULL, NULL, NULL, NULL, NULL, '2019-04-22', '2019-04-22', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (105, '3061', 'NGÔ THẾ HIỂN', 'hiennt1@runsystem.net', 1, '549/28/20 Lê Văn Thọ, P.14, Quận Gò Vấp, HCM', '0973185125', '1987-04-12', 'TIỀN GIANG', NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-30', '2019-04-01', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (106, '1854', 'NGUYỄN QUỐC HÙNG', 'hungnq@runsystem.net', 1, 'C5/20 Phạm Hùng, Bình Hưng, Bình Chánh, TPHCM', NULL, '1989-08-15', 'Đắk Lắk', NULL, NULL, NULL, NULL, NULL, NULL, '2014-08-22', '2014-10-22', 'Chi Nhánh Hồ Chí Minh', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (107, '1723', 'VƯƠNG THÁI HUỲNH', 'huynhvt@runsystem.net', 1, '37 Thép Mới, P12, Quận Tân Bình TP HCM', '0773424767', '1989-05-04', 'Bạc Liêu', NULL, NULL, NULL, NULL, NULL, NULL, '2013-11-18', '2014-01-18', 'Chi nhánh Hồ Chí Minh', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (108, '1147', 'NGUYỄN THỊ THANH THẢO', 'thaontt@runsystem.net', 0, '62/15 Điện Biên Phủ', '0916469836', '1983-03-07', 'Bến Tre', NULL, NULL, NULL, NULL, 'Quận Bình Thạnh', 'Phường 17', '2008-09-29', '2008-11-29', 'HCM', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 2, '', '', '');
INSERT INTO `employees` VALUES (109, '1796', 'PHẠM VŨ DUY NAM', 'nampvd@runsystem.net', 1, '309/3 Nguyễn Thái Sơn, P5, Quận Gò Vấp, TP. HCM', '0388370099', '1992-01-09', 'Bà Rịa-Vũng Tàu', NULL, NULL, NULL, NULL, NULL, NULL, '2014-05-26', '2014-09-01', 'HCM', 3, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (110, '2828', 'HUỲNH QUỐC KHÁNH', 'khanhhq@runsystem.net', 1, 'A13-07, Chung Cư Lavita Garden, Đường số 3, Phường Trường Thọ, Quận Thủ Đức, Tp. Hồ Chí Minh', '0918393438', '1983-11-01', 'Huyện Phù Mỹ- Tỉnh Bình Định', NULL, NULL, NULL, NULL, NULL, NULL, '2018-07-02', '2019-02-01', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 1, 4, '', '', '');
INSERT INTO `employees` VALUES (111, '2452', 'ĐINH NGỌC HUYỀN', 'huyendn@runsystem.net', 0, '295 Tân Kỳ Tân Quý, Tân Sơn Nhì, Tân Phú', '0982298513', '1983-09-22', 'Đồng Hới-Quảng Bình', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-05', '2017-08-04', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 5, 4, '', '', '');
INSERT INTO `employees` VALUES (112, '2875', 'NGUYỄN NHẬT KHÁNH TRÂN', 'trannnk@runsystem.net', 0, '144 Bùi Văn Ngữ- p Hiệp Thành - q12', '02593871137', '1996-02-02', 'Phan Rang', NULL, NULL, NULL, NULL, NULL, NULL, '2018-07-30', '2019-01-01', 'Chi Nhánh Hồ Chí Minh', 1, 1, NULL, NULL, 0, '2019-12-11 09:25:30', '2019-12-11 09:25:30', 5, 18, '', '', '');

-- ----------------------------
-- Table structure for m_division
-- ----------------------------
DROP TABLE IF EXISTS `m_division`;
CREATE TABLE `m_division`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `division_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_division
-- ----------------------------
INSERT INTO `m_division` VALUES (1, 'Ho Chi Minh Branch', 0, '2019-09-30 10:00:35', '2019-09-30 10:00:40');
INSERT INTO `m_division` VALUES (2, 'HCM - Smartgift', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (3, 'Enterprise Solutions Division (ESD)', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (4, 'HCM - R&D', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (5, 'HCM - QA/QC', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (6, 'HCM - Nikko', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (7, 'IID-Accounting HCM', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (8, 'IID-Money Maker Team (HCM)', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_division` VALUES (9, 'IID-Policy & Terms (HCM)', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');

-- ----------------------------
-- Table structure for m_position
-- ----------------------------
DROP TABLE IF EXISTS `m_position`;
CREATE TABLE `m_position`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `position_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_position
-- ----------------------------
INSERT INTO `m_position` VALUES (1, 'Branch Director', 0, '2019-09-30 10:00:59', '2019-09-30 10:01:03');
INSERT INTO `m_position` VALUES (2, 'Department Manager', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (3, 'Project Manager', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (4, 'BrSE', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (5, 'QA Executive', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (6, 'Team Leader', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (7, 'Business Analyst', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (8, 'Developer', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (9, 'Tester', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (10, 'Comtor', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (11, 'Đào tạo', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (12, 'Học việc', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (13, 'Thử việc', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (14, 'Sale Executive', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (15, 'Member of BOM', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (16, 'R&D Engineer', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (17, 'Admin Executive', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (18, 'HR Executive', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (19, 'Creative editor', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (20, 'Accountant', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (21, 'Policy & Terms Executive', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (22, 'Network Administrator', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (23, 'Cộng tác viên', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (24, 'Assistant', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');
INSERT INTO `m_position` VALUES (25, 'Nhân viên tạp vụ', 0, '2019-09-30 10:00:59', '2019-09-30 10:00:59');

-- ----------------------------
-- Table structure for m_status
-- ----------------------------
DROP TABLE IF EXISTS `m_status`;
CREATE TABLE `m_status`  (
  `id` int(11) NOT NULL,
  `unit_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_status
-- ----------------------------
INSERT INTO `m_status` VALUES (1, 'Đang làm việc', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (2, 'Không có việc làm', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (3, 'Đang làm thủ tục thôi việc', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (4, 'Đình chỉ công tác', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (5, 'Nghỉ hưu', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (6, 'Nghỉ việc', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (7, 'Nghỉ khác', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (8, 'Mất việc làm', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (9, 'Sa thải', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (10, 'Thực tập sinh', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (11, 'Học việc', NULL, NULL, 0);
INSERT INTO `m_status` VALUES (12, 'Thử việc', NULL, NULL, 0);

-- ----------------------------
-- Table structure for m_type_contact
-- ----------------------------
DROP TABLE IF EXISTS `m_type_contact`;
CREATE TABLE `m_type_contact`  (
  `id` int(11) NOT NULL,
  `type_contact_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_type_contact
-- ----------------------------
INSERT INTO `m_type_contact` VALUES (1, 'Hợp đồng xác định thời hạn', NULL, NULL, 0);
INSERT INTO `m_type_contact` VALUES (2, 'Thử việc', NULL, NULL, 0);
INSERT INTO `m_type_contact` VALUES (3, 'Hợp đồng không xác định thời hạn', NULL, NULL, 0);
INSERT INTO `m_type_contact` VALUES (4, 'Học việc', NULL, NULL, 0);
INSERT INTO `m_type_contact` VALUES (5, 'Hợp đồng mùa vụ', NULL, NULL, 0);
INSERT INTO `m_type_contact` VALUES (6, 'Hợp đồng dịch vụ', NULL, NULL, 0);

-- ----------------------------
-- Table structure for ot_time_approval
-- ----------------------------
DROP TABLE IF EXISTS `ot_time_approval`;
CREATE TABLE `ot_time_approval`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `project_code` int(10) NOT NULL,
  `position_code` int(10) NOT NULL,
  `date_ot` date NOT NULL,
  `approve_time_ot` int(2) NULL DEFAULT NULL,
  `request_id` int(10) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `delete_flag` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `request_id`(`request_id`) USING BTREE,
  CONSTRAINT `ot_time_approval_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ot_time_approval
-- ----------------------------
INSERT INTO `ot_time_approval` VALUES (1, '2395', 1, 3, '2020-01-15', 2, 80, '2020-01-14 11:50:41', '2020-01-14 11:50:41', 0);
INSERT INTO `ot_time_approval` VALUES (2, '2395', 1, 4, '2020-01-15', 1, 80, '2020-01-14 11:50:41', '2020-01-14 11:50:41', 0);
INSERT INTO `ot_time_approval` VALUES (3, '1827', 1, 6, '2020-01-15', 3, 80, '2020-01-14 11:50:41', '2020-01-14 11:50:41', 0);
INSERT INTO `ot_time_approval` VALUES (4, '2395', 4, 6, '2020-01-16', 4, 82, '2020-01-14 13:32:47', '2020-01-14 13:55:58', 0);
INSERT INTO `ot_time_approval` VALUES (5, '2395', 1, 3, '2020-01-17', 2, 83, '2020-01-14 15:20:43', '2020-01-14 15:20:43', 0);
INSERT INTO `ot_time_approval` VALUES (6, '2395', 1, 4, '2020-01-17', 2, 83, '2020-01-14 15:20:43', '2020-01-14 15:20:43', 0);
INSERT INTO `ot_time_approval` VALUES (7, '1827', 4, 3, '2020-01-17', 0, 89, '2020-01-20 10:35:36', '2020-01-20 14:39:20', 1);
INSERT INTO `ot_time_approval` VALUES (8, '2533', 4, 4, '2020-01-16', 0, 89, '2020-01-20 10:35:36', '2020-01-20 14:39:20', 1);
INSERT INTO `ot_time_approval` VALUES (9, '0002', 1, 8, '2020-01-19', 3, 87, '2020-01-20 10:36:06', '2020-01-20 14:18:07', 1);
INSERT INTO `ot_time_approval` VALUES (10, '2533', 4, 4, '2020-01-18', 2, 86, '2020-01-20 10:37:34', '2020-01-20 10:37:34', 0);
INSERT INTO `ot_time_approval` VALUES (11, '2395', 4, 6, '2020-01-18', 2, 86, '2020-01-20 10:37:34', '2020-01-20 10:37:34', 0);
INSERT INTO `ot_time_approval` VALUES (12, '3096', 4, 8, '2020-01-18', 2, 86, '2020-01-20 10:37:34', '2020-01-20 10:37:34', 0);
INSERT INTO `ot_time_approval` VALUES (13, '0001', 4, 5, '2020-01-15', 2, 84, '2020-01-20 14:02:24', '2020-01-20 14:02:24', 0);
INSERT INTO `ot_time_approval` VALUES (14, '2395', 4, 5, '2020-01-17', 2, 85, '2020-01-20 14:17:55', '2020-01-20 14:17:55', 0);
INSERT INTO `ot_time_approval` VALUES (15, '2533', 4, 4, '2020-01-17', 2, 85, '2020-01-20 14:17:55', '2020-01-20 14:17:55', 0);
INSERT INTO `ot_time_approval` VALUES (16, '1827', 4, 3, '2020-01-17', 3, 89, '2020-01-20 14:42:30', '2020-01-20 14:42:30', 0);
INSERT INTO `ot_time_approval` VALUES (17, '2533', 4, 4, '2020-01-16', 2, 89, '2020-01-20 14:42:30', '2020-01-20 14:42:30', 0);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `project_code` int(10) NOT NULL AUTO_INCREMENT,
  `project_name_jp` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_name_vn` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `billable_effort` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NULL DEFAULT NULL,
  `customer_name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sale` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_rank` int(10) NULL DEFAULT NULL,
  `scope` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `objectives` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email_cc` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `delete_flag` int(1) NOT NULL,
  PRIMARY KEY (`project_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, 'BI Tool', 'BI Tool', '100mm', '2019-12-01', '2020-01-01', 'Willgroup', 'Nguyễn Văn A', 1, 'BD + DD + Code + UT + IT', 'BD + DD + Code + UT + IT', 'a@gmail.com,c@gmail.com,b@gmail.com', '2019-12-27 13:48:15', '2020-01-15 09:44:54', 0);
INSERT INTO `project` VALUES (2, '', 'Aviva', '5mm', '2019-12-01', NULL, 'Aviva VN', 'Nguyễn Văn A', 4, 'Code + UT', 'Code + UT', '', '2019-12-31 10:21:37', '2019-12-31 15:29:25', 0);
INSERT INTO `project` VALUES (4, 'キレイパス', 'kireipass', 'Labo Dev 2mm/ 1month ', '2019-11-01', NULL, 'GMO Kumapon', 'Nguyễn Văn Hòa', 0, 'CD+UT+IT', 'Pass các yêu cầu của KH', '', '2020-01-02 09:28:57', '2020-01-09 16:07:13', 0);

-- ----------------------------
-- Table structure for project_detail
-- ----------------------------
DROP TABLE IF EXISTS `project_detail`;
CREATE TABLE `project_detail`  (
  `project_code` int(10) NOT NULL,
  `employee_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position_code` int(10) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `delete_flag` int(1) NOT NULL,
  PRIMARY KEY (`project_code`, `employee_code`, `position_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_detail
-- ----------------------------
INSERT INTO `project_detail` VALUES (1, '0001', 8, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '0002', 8, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '1827', 6, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '1834', 8, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '2395', 3, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '2395', 4, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '2734', 7, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '2875', 5, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (1, '3096', 12, '2020-01-15 09:44:54', '2020-01-15 09:44:54', 0);
INSERT INTO `project_detail` VALUES (2, '1827', 6, '2019-12-31 15:29:25', '2019-12-31 15:29:25', 0);
INSERT INTO `project_detail` VALUES (2, '1835', 3, '2019-12-31 15:29:25', '2019-12-31 15:29:25', 0);
INSERT INTO `project_detail` VALUES (2, '2395', 4, '2019-12-31 15:29:25', '2019-12-31 15:29:25', 0);
INSERT INTO `project_detail` VALUES (2, '3096', 4, '2019-12-31 15:29:25', '2019-12-31 15:29:25', 0);
INSERT INTO `project_detail` VALUES (4, '0001', 5, '2020-01-09 16:07:13', '2020-01-09 16:07:13', 0);
INSERT INTO `project_detail` VALUES (4, '1827', 3, '2020-01-09 16:07:13', '2020-01-09 16:07:13', 0);
INSERT INTO `project_detail` VALUES (4, '2395', 6, '2020-01-09 16:07:13', '2020-01-09 16:07:13', 0);
INSERT INTO `project_detail` VALUES (4, '2533', 4, '2020-01-09 16:07:13', '2020-01-09 16:07:13', 0);
INSERT INTO `project_detail` VALUES (4, '3096', 8, '2020-01-09 16:07:13', '2020-01-09 16:07:13', 0);

-- ----------------------------
-- Table structure for project_rank
-- ----------------------------
DROP TABLE IF EXISTS `project_rank`;
CREATE TABLE `project_rank`  (
  `id` int(10) NOT NULL,
  `rank` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `delete_flag` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_rank
-- ----------------------------
INSERT INTO `project_rank` VALUES (1, 'A', '>30MM', '2019-12-13 11:43:48', '2019-12-13 11:43:53', 0);
INSERT INTO `project_rank` VALUES (2, 'B', '> 20 MM & <= 30 MM', '2019-12-13 11:44:21', '2019-12-13 11:44:24', 0);
INSERT INTO `project_rank` VALUES (3, 'C', '> 10 MM & <= 20 MM', '2019-12-13 11:44:40', '2019-12-13 11:44:43', 0);
INSERT INTO `project_rank` VALUES (4, 'D', '<= 10 MM', '2019-12-13 11:44:58', '2019-12-13 11:45:01', 0);

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `project_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `reason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `note` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` int(1) NOT NULL DEFAULT 1,
  `approver` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `employee_id`(`employee_code`) USING BTREE,
  INDEX `approver`(`approver`) USING BTREE,
  CONSTRAINT `request_fk_1` FOREIGN KEY (`employee_code`) REFERENCES `employees` (`employee_code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES (80, '2395', '1', 'KH feedback trễ', NULL, 3, '2395', 0, '2020-01-14 11:46:16', '2020-01-14 11:50:41');
INSERT INTO `request` VALUES (81, '2395', '4', 'KH', NULL, 3, '2395', 0, '2020-01-14 11:46:50', '2020-01-14 13:32:46');
INSERT INTO `request` VALUES (82, '2395', '4', 'abc', NULL, 3, '2395', 0, '2020-01-14 13:54:40', '2020-01-14 13:55:58');
INSERT INTO `request` VALUES (83, '2395', '1', 'TEST', NULL, 3, '2395', 0, '2020-01-14 14:16:08', '2020-01-14 15:20:42');
INSERT INTO `request` VALUES (84, '1827', '4', '3333', NULL, 3, '2395', 0, '2020-01-14 14:27:30', '2020-01-20 14:02:24');
INSERT INTO `request` VALUES (85, '1827', '4', '434', NULL, 3, '2395', 0, '2020-01-14 14:28:06', '2020-01-20 14:17:55');
INSERT INTO `request` VALUES (86, '1827', '4', '544', NULL, 3, '2395', 0, '2020-01-14 14:29:00', '2020-01-20 10:37:34');
INSERT INTO `request` VALUES (87, '1827', '1', 'fff', NULL, 1, '2395', 0, '2020-01-14 14:29:28', '2020-01-20 14:18:07');
INSERT INTO `request` VALUES (88, '2395', '1', 'Kh', NULL, 2, '1827', 0, '2020-01-15 09:30:17', '2020-01-20 15:23:45');
INSERT INTO `request` VALUES (89, '1827', '4', 'ddddđsd\nđâs\nđâs\nđâs\nđâs\nđâs\nđá\n', NULL, 3, '2395', 0, '2020-01-16 15:08:12', '2020-01-20 14:42:30');

-- ----------------------------
-- Table structure for request_detail
-- ----------------------------
DROP TABLE IF EXISTS `request_detail`;
CREATE TABLE `request_detail`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `request_id` int(10) NOT NULL,
  `employee_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `position_code` int(10) NOT NULL,
  `date_ot` date NOT NULL,
  `plan_time_ot` int(2) NOT NULL DEFAULT 0,
  `actual_time_ot` int(2) NULL DEFAULT 0,
  `approval_time_ot` int(2) NULL DEFAULT 0,
  `note` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `employee_id`(`employee_code`) USING BTREE,
  INDEX `request_id`(`request_id`) USING BTREE,
  CONSTRAINT `request_detail_ibfk_1` FOREIGN KEY (`employee_code`) REFERENCES `employees` (`employee_code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `request_detail_ibfk_2` FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request_detail
-- ----------------------------
INSERT INTO `request_detail` VALUES (1, 80, '2395', 3, '2020-01-15', 2, 0, 2, '', 0, '2020-01-14 11:46:16', '2020-01-14 11:48:49');
INSERT INTO `request_detail` VALUES (2, 80, '2395', 4, '2020-01-15', 2, 0, 1, '', 0, '2020-01-14 11:46:16', '2020-01-14 11:48:49');
INSERT INTO `request_detail` VALUES (3, 80, '1827', 6, '2020-01-15', 3, 0, 3, '', 0, '2020-01-14 11:46:16', '2020-01-14 11:48:49');
INSERT INTO `request_detail` VALUES (4, 81, '2395', 6, '2020-01-16', 3, 0, 3, '', 0, '2020-01-14 11:46:50', '2020-01-14 11:47:49');
INSERT INTO `request_detail` VALUES (5, 82, '2395', 6, '2020-01-16', 4, 0, 4, '', 0, '2020-01-14 13:54:40', '2020-01-14 13:55:21');
INSERT INTO `request_detail` VALUES (6, 83, '2395', 3, '2020-01-17', 2, 0, 2, '', 0, '2020-01-14 14:16:08', '2020-01-14 14:43:58');
INSERT INTO `request_detail` VALUES (7, 83, '2395', 4, '2020-01-17', 2, 0, 2, '', 0, '2020-01-14 14:16:08', '2020-01-14 14:43:58');
INSERT INTO `request_detail` VALUES (8, 84, '0001', 5, '2020-01-15', 4, 0, 2, '', 0, '2020-01-14 14:27:30', '2020-01-14 14:44:34');
INSERT INTO `request_detail` VALUES (9, 85, '2395', 5, '2020-01-17', 4, 0, 2, '', 0, '2020-01-14 14:28:06', '2020-01-14 14:44:22');
INSERT INTO `request_detail` VALUES (10, 85, '2533', 4, '2020-01-17', 5, 0, 2, '', 0, '2020-01-14 14:28:06', '2020-01-14 14:44:22');
INSERT INTO `request_detail` VALUES (11, 86, '2533', 4, '2020-01-18', 5, 0, 2, '', 0, '2020-01-14 14:29:00', '2020-01-14 14:44:10');
INSERT INTO `request_detail` VALUES (12, 86, '2395', 6, '2020-01-18', 4, 0, 2, '', 0, '2020-01-14 14:29:00', '2020-01-14 14:44:10');
INSERT INTO `request_detail` VALUES (13, 86, '3096', 8, '2020-01-18', 4, 0, 2, '', 0, '2020-01-14 14:29:00', '2020-01-14 14:44:10');
INSERT INTO `request_detail` VALUES (14, 87, '0002', 8, '2020-01-19', 5, 0, 0, '', 0, '2020-01-14 14:29:28', '2020-01-20 14:18:07');
INSERT INTO `request_detail` VALUES (15, 88, '1827', 6, '2020-01-17', 2, 0, 2, 'test abc', 0, '2020-01-15 09:30:17', '2020-01-20 15:23:45');
INSERT INTO `request_detail` VALUES (16, 89, '1827', 3, '2020-01-17', 4, 0, 3, 'test', 0, '2020-01-16 15:08:12', '2020-01-20 14:41:59');
INSERT INTO `request_detail` VALUES (17, 89, '2533', 4, '2020-01-16', 2, 0, 2, 'test', 0, '2020-01-16 15:08:12', '2020-01-20 14:41:59');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(1) NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN', 0, '2019-09-27 14:11:04', '2019-09-27 14:11:07');
INSERT INTO `role` VALUES (2, 'SUB_ADMIN', 0, '2019-09-27 14:11:04', '2019-09-27 14:11:07');
INSERT INTO `role` VALUES (3, 'USER', 0, '2019-09-27 14:11:04', '2019-09-27 14:11:07');
INSERT INTO `role` VALUES (4, 'QA', 0, '2019-09-27 14:11:04', '2019-09-27 14:11:07');

SET FOREIGN_KEY_CHECKS = 1;

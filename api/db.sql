DROP TABLE IF EXISTS `order_shipment`;
CREATE TABLE `order_shipment`  (
                                   `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                                   `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
                                   `name_recv` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'receiver name',
                                   `weight_box` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT 'weight  in kilograms',
                                   `color_box` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'box colour, RGB-format, i.e.  rgb(255, 255, 255)',
                                   `country_name_dest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'destination country short name from ISO 3166-1',
                                   `cost_shipment` decimal(12, 2) NULL DEFAULT 0.00 COMMENT 'shipping cost. weight_box * country_dest(the multiplier)',
                                   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `ios_oi`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `country_multiplier`;
CREATE TABLE `country_multiplier`  (
                                       `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                                       `country_name_dest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'destination country short name from ISO 3166-1',
                                       `country_multiplier` decimal(12, 2) NULL DEFAULT NULL,
                                       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                       `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `country_multiplier` VALUES (1, 'Sweden', 1.30, '2021-09-16 14:51:58', NULL);
INSERT INTO `country_multiplier` VALUES (2, 'China', 4.00, '2021-09-16 14:52:04', NULL);
INSERT INTO `country_multiplier` VALUES (3, 'Brazil', 8.60, '2021-09-16 14:52:16', NULL);
INSERT INTO `country_multiplier` VALUES (4, 'Australia', 7.20, '2021-09-16 14:52:30', NULL);


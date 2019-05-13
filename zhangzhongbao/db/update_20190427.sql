-- ----------------------------
-- 商品价格区间
-- ----------------------------
ALTER TABLE `t_task_mould`
ADD COLUMN `price_from`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间小' AFTER `price`,
ADD COLUMN `price_to`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间大' AFTER `price_from`;
ALTER TABLE `t_task`
ADD COLUMN `price_from`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间小' AFTER `price`,
ADD COLUMN `price_to`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间大' AFTER `price_from`;
ALTER TABLE `t_mission_detail`
ADD COLUMN `price_from`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间小' AFTER `other_note`,
ADD COLUMN `price_to`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品价格区间大' AFTER `price_from`;

-- ----------------------------
-- 产品主图1,2,3
-- ----------------------------
ALTER TABLE `t_task_mould`
MODIFY COLUMN `img`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图一' AFTER `other_note`,
ADD COLUMN `img2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图二' AFTER `img`,
ADD COLUMN `img3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图三' AFTER `img2`;

ALTER TABLE `t_task`
MODIFY COLUMN `img`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图一' AFTER `seller_name`,
ADD COLUMN `img2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图二' AFTER `img`,
ADD COLUMN `img3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图三' AFTER `img2`;

ALTER TABLE `t_mission_detail`
MODIFY COLUMN `img`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图一' AFTER `other_note`,
ADD COLUMN `img2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图二' AFTER `img`,
ADD COLUMN `img3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图三' AFTER `img2`;

-- ----------------------------
-- 关键字表
-- ----------------------------
CREATE TABLE `t_key_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint(20) DEFAULT '0' COMMENT '任务Id',
  `task_mould_id` bigint(20) DEFAULT '0' COMMENT '任务模版Id',
  `key_word` varchar(500) NOT NULL COMMENT '搜索关键词',
  `amount` int(8) DEFAULT '0' COMMENT '关键词放至任务数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务关键字表';

ALTER TABLE `t_key_word`
ADD COLUMN `sort`  int(8) NULL DEFAULT 0 COMMENT '排序' AFTER `amount`,
ADD COLUMN `create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `sort`;



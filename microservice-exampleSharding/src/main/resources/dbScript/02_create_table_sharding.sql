-- 分库测试数据库表创建脚本
drop table  if exists goods_info;
drop table  if exists goods_info_details;
drop table  if exists user_info;
drop table  if exists user_orders;

CREATE TABLE `goods_info` (
  `goods_id` BIGINT NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品主表';


CREATE TABLE `goods_info_details` (
  `id` BIGINT NOT NULL,
  `goods_id` BIGINT  COMMENT 'goods_info主键',
  `price` int(11) DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表';

CREATE TABLE `user_info` (
  `user_id` BIGINT NOT NULL ,
  `name` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `user_orders` (
  `order_id` BIGINT NOT NULL ,
  `user_id` BIGINT COMMENT '用户id',
  `price` int(11) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订单表';

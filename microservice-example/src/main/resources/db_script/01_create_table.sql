
drop table  if exists user_info;
CREATE TABLE `user_info` (
  `user_id` BIGINT NOT NULL ,
  `name` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

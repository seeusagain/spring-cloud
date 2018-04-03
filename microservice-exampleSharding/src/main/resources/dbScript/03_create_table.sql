-- 分别在两个库创建两张不同的表，然后测试：1.使用默认数据库不分库存取数据；2.动态切换数据源

-- ds_0库（默认库）中执行：
drop table  if exists default_test;

CREATE TABLE `default_test` (
  `test_id` BIGINT NOT NULL,
  `name` varchar(100) ,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='默认库测试表';


-- ds_1库中执行：
drop table  if exists switch_test;

CREATE TABLE `switch_test` (
 `test_id` BIGINT NOT NULL,
  `name` varchar(100) ,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='切换数据源测试表';

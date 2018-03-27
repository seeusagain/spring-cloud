
-- 创建数据库
CREATE DATABASE shardingtestdb CHARACTER SET 'UTF8';

-- 创建用户
CREATE USER shardingTest
   IDENTIFIED BY 'shardingTest';

-- 授权
GRANT ALL PRIVILEGES ON  shardingtestdb.* TO 'shardingTest'@'%'
IDENTIFIED BY 'shardingTest' WITH GRANT OPTION;

 -- 立即生效
FLUSH PRIVILEGES;
#数据库初始化版本

#创建数据库

CREATE DATABASE seckill;
#使用数据库
use seckill;
#创建秒杀库存表
CREATE TABLE seckill(
'seckill_id' BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
'name' VARCHAR(120) NOT NULL COMMENT '商品名称',
'number' INT  NOT NULl COMMENT '库存数量',
'start_time' timestamp NOT NULL COMMENT '秒杀开启时间',
'end_time' timestamp NOT NULL COMMENT '秒杀结束时间',
'create_time' timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGIINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';


#初始化数据
insert into seckill(name,number,start_time,end_time)
values
   ('1000元秒杀iphone6',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
   ('500元秒杀ipad2',200,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
   ('300元秒杀小米6',300,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
   ('200元秒杀红米6',400,'2018-11-01 00:00:00','2018-11-02 00:00:00');


#秒杀成功明细表
 #用户登录认证相关信息
 create table sucess_killed(
'seckill_id' BIGINT not null COMMENT '秒杀商品ID',
'user_phone' BIGINT not null COMMENT '用户手机号',
'state' tinyint not null default -1 COMMENT '状态标志：-1：无效  0：成功 1：已付款',
'create_time' timestamp not null default COMMENT '创建时间',
primary key (seckill_id,user_phone),
key idx_create_time(create_time)
 )ENGIINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

#连接数据库控制台
 mysql -uroot -p




package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    /**
     * 第一次执行返回insetCount=1
     * 第二次（说明1000L这个产品又被秒杀一次）insetCount=0，即插入失败；
     * 其实是user和userphone组成的唯一键起作用，还有防止主键冲突的ignore
     */
    public void testInsertSuccessKilled()throws Exception{
        long id = 1001L;
        long phone = 1350061235L;
        int insetCount = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insetCount="+insetCount);
    }
    @Test
    public void testQueryByIdWithSeckill()throws Exception{
        long id =1001L;
        long phone = 1350061235L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
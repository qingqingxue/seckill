package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    /**

     */
    @Test
    public  void testGetSeckillList()throws Exception{
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("seckill={}",list);
    }
    @Test
    public void testGetById() throws Exception{
        long id = 1000L;
        Seckill seckill =  seckillService.getById(id);
        System.out.println(seckill);
        logger.info("seckill={}",seckill);
    }

    @Test
    //集成测试
    public void  testSeckillLogic()throws Exception{   //testExportSeckillUrl
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){ //秒杀成功
            logger.info("exposer={}",exposer);
            long phone = 13500612351L;
            String md5 = exposer.getMd5();
            try{
                SeckillExcution seckillExcution = seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",seckillExcution);
            }catch(RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exporse={}",exposer);
        }


        //f416e630031c7d39f799b4be5ac28871
       // exposed=true, md5='f416e630031c7d39f799b4be5ac28871', seckillId=1000, now=0, start=0
    }

    @Test
    public void testExcuteSeckill()throws Exception{
        long id = 1000L;
        long phone = 13500612351L;
        String md5 = "f416e630031c7d39f799b4be5ac28871";
       try{
           SeckillExcution seckillExcution = seckillService.executeSeckill(id,phone,md5);
           logger.info("result={}",seckillExcution);
       }catch(RepeatKillException e){
          logger.error(e.getMessage());
       }catch (SeckillCloseException e){
           logger.error(e.getMessage());
       }
    }

}
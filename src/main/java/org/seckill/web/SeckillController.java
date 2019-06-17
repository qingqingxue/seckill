package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Component       //@Service  @Component
@RequestMapping("/seckill")  //url:/模块/资源/{id}/细分/seckill/list
public class SeckillController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)                             //二级请求
    public String list(Model model){
        // 获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        //list+jsp +model =ModelAndView     存放秒杀商品列表的渲染数据（model）

        //不满足GET请求方式的全部驳回
        return "list";             // /WEB-INF/jsp/list.jsp
    }
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)  //seckillId占位符，所以参数里要标注一下
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){  //秒杀商品详情页
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
        return  "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return  "detail";
        }

        // TODO  输出秒杀接口
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = "application/json;chaerset=UTF-8")
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return  result;
        }
        @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces ="application/json;chaerset=UTF-8" )
        @ResponseBody
        //执行秒杀操作
        public SeckillResult<SeckillExcution> execute(@PathVariable("seckillId") Long seckillId,
                                                      @PathVariable("md5") String md5,
                                                      @CookieValue(value = "killPhone",required = false) Long phone){
        //killPhone是从浏览器的cookie中国获取，如果没有的话会报错，所以设置为不是必须的
            //springmvc的验证信息
           if (phone == null){
               return new SeckillResult<SeckillExcution>(false,"未注册");
           }
            SeckillResult<SeckillExcution> result;
            try{
                SeckillExcution excution = seckillService.executeSeckill(seckillId,phone,md5);
                return new SeckillResult<SeckillExcution>(true,excution);
            }catch (RepeatKillException e){
                SeckillExcution excution = new SeckillExcution(seckillId, SeckillStateEnum.REPEATE_KILL);
                return new SeckillResult<SeckillExcution>(false,excution);
            }catch (SeckillCloseException e){
                SeckillExcution excution = new SeckillExcution(seckillId, SeckillStateEnum.END);
                return new SeckillResult<SeckillExcution>(false,excution);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                SeckillExcution excution = new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
                return new SeckillResult<SeckillExcution>(false,excution);
            }
        }
        //获取系统时间
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
        public SeckillResult<Long> time(){
        Date now = new Date();
        return  new SeckillResult(true,now.getTime());

        }
    }


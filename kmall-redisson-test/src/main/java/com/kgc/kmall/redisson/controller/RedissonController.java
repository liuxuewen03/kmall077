package com.kgc.kmall.redisson.controller;

import com.kgc.kmall.utils.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2021-01-04 14:31
 */
@RestController
public class RedissonController {


    RedisUtil redisUtil=new RedisUtil();

    @RequestMapping("test")
    public String test01(){
        redisUtil.initPool("192.168.191.120",6379,0);
        Jedis jedis=redisUtil.getJedis();
        try {
            String v=jedis.get("k");
            if (v==null){
                v="1";
            }
            System.out.println("27----"+v);
            jedis.set("k",(Integer.parseInt(v)+1)+"");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return "测试";

    }



}

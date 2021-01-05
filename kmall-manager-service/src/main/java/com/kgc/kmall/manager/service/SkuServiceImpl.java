package com.kgc.kmall.manager.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall.manager.mapper.PmsSkuImageMapper;
import com.kgc.kmall.manager.mapper.PmsSkuInfoMapper;
import com.kgc.kmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall.service.SkuService;
import com.kgc.kmall.utils.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author shkstart
 * @create 2020-12-24 8:40
 */
@Component
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Resource
    PmsSkuImageMapper pmsSkuImageMapper;
    @Resource
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Resource
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Resource
    RedisUtil redisUtil;
    @Resource
    RedissonClient redissonClient;

    @Override
    public String saveSkuInfo(PmsSkuInfo skuInfo) {
        pmsSkuInfoMapper.insert(skuInfo);
        Long skuInfoId = skuInfo.getId();
        for (PmsSkuImage pmsSkuImage : skuInfo.getSkuImageList()) {
            pmsSkuImage.setSkuId(skuInfoId);
            pmsSkuImageMapper.insert(pmsSkuImage);
        }
        for (PmsSkuAttrValue pmsSkuAttrValue : skuInfo.getSkuAttrValueList()) {
            pmsSkuAttrValue.setSkuId(skuInfoId);
            pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
        }
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
            pmsSkuSaleAttrValue.setSkuId(skuInfoId);
            pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }
        return "success";
    }

    @Override
    public PmsSkuInfo selectBySkuId(Long skuId) {
        PmsSkuInfo pmsSkuInfo = null;
        Jedis jedis = redisUtil.getJedis();
        String skuKey = "sku:" + skuId + ":info";
        String skuInfoJson = jedis.get(skuKey);

        if (skuInfoJson != null) {
            pmsSkuInfo = JSON.parseObject(skuInfoJson, PmsSkuInfo.class);
            jedis.close();
            return pmsSkuInfo;
        } else {
            //获取分布式锁
            //使用nx分布式锁，避免缓存击穿
            RLock lock = redissonClient.getLock("lock");
            lock.lock();//上锁
            try {
                pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
                //保存到redis
                if (pmsSkuInfo != null) {
                    String skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                    //有效期随机，防止缓存雪崩
                    Random random = new Random();
                    int i = random.nextInt(10);
                    jedis.setex(skuKey, i * 60 * 1000, skuInfoJsonStr);
                } else {
                    jedis.setex(skuKey, 5 * 60 * 1000, "empty");
                }
                jedis.close();

            } finally {
                lock.unlock();
            }

        }
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> selectBySpuId(Long spuId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectBySpuId(spuId);
        return pmsSkuInfos;
    }
}

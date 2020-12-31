package com.kgc.kmall.manager.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall.manager.mapper.PmsSkuImageMapper;
import com.kgc.kmall.manager.mapper.PmsSkuInfoMapper;
import com.kgc.kmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall.manager.utils.RedisUtil;
import com.kgc.kmall.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import springfox.documentation.spring.web.json.Json;

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
    public PmsSkuInfo selectBySkuId(Long skuid) {
        Jedis jedis = redisUtil.getJedis();
        String key = "sku:" + skuid + ":info";
        String skuJson = jedis.get(key);
        PmsSkuInfo pmsSkuInfo = null;
        if (skuJson != null) {
            //缓存有数据
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
            jedis.close();
            return pmsSkuInfo;
        } else {
            String skuLockKey = "sku:" + skuid + ":lock";
            String skuLockValue= UUID.randomUUID().toString();
            //获取分布式锁
            String skuKey = jedis.set(skuLockKey, skuLockValue, "NX", "PX", 60 * 1000);
            //是否拿到分布式锁
            if (skuKey.equals("OK")) {
                //缓存没数据
                pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuid);
                PmsSkuImageExample pmsSkuImageExample = new PmsSkuImageExample();
                pmsSkuImageExample.createCriteria().andSkuIdEqualTo(pmsSkuInfo.getId());
                List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectByExample(pmsSkuImageExample);
                pmsSkuInfo.setSkuImageList(pmsSkuImages);
                if (pmsSkuInfo != null) {
                    String json = JSON.toJSONString(pmsSkuInfo);
                    //设置随机有效期，防止雪崩
                    Random random = new Random();
                    int i = random.nextInt(10);
                    jedis.setex(key, i * 60 * 1000, skuJson);
                } else {
                    jedis.setex(key, 5 * 60 * 1000, "empty");
                }
               /* //写完缓存后，删除分布式锁
                String skuLockValue2 = jedis.get(skuLockKey);
                if (skuLockValue2!=null&&skuLockValue2.equals(skuLockValue)){
                    //刚做完判断，过期了
                    jedis.del(skuLockKey);
                }*/
                String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList(skuLockKey),Collections.singletonList(skuLockValue));

            } else {
                //未拿到锁，进行线程睡眠3s，递归调用
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                return selectBySkuId(skuid);
            }
            jedis.close();
        }
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> selectBySpuId(Long spuId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectBySpuId(spuId);
        return pmsSkuInfos;
    }
}

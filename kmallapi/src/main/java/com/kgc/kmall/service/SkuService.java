package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsSkuInfo;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-24 8:40
 */

public interface SkuService {

    public String saveSkuInfo(PmsSkuInfo skuInfo);

    PmsSkuInfo selectBySkuId(Long skuId);

    List<PmsSkuInfo> selectBySpuId(Long spuId);
}

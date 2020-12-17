package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 14:16
 */
public interface SpuService {

    List<PmsProductInfo> spuList(Long catalog3Id);

    List<PmsProductSaleAttr> baseSaleAttrList();

    int saveSpuInfo(PmsProductInfo pmsProductInfo);


}

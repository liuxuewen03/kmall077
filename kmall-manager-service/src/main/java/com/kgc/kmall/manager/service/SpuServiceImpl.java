package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductInfoExample;
import com.kgc.kmall.bean.PmsProductSaleAttr;
import com.kgc.kmall.manager.mapper.PmsProductInfoMapper;
import com.kgc.kmall.manager.mapper.PmsProductSaleAttrMapper;
import com.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 14:18
 */
@Component
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;
    @Resource
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;


    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample pmsProductInfoExample = new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = pmsProductInfoExample.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.selectByExample(pmsProductInfoExample);
        return pmsProductInfos;
    }

    @Override
    public List<PmsProductSaleAttr> baseSaleAttrList() {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectByExample(null);
        return pmsProductSaleAttrs;
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {


        return -1;
    }
}

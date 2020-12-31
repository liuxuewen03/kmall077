package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.*;
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
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Resource
    PmsProductImageMapper pmsProductImageMapper;
    @Resource
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Resource
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample pmsProductInfoExample = new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = pmsProductInfoExample.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.selectByExample(pmsProductInfoExample);
        return pmsProductInfos;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsProductSaleAttrs = pmsBaseSaleAttrMapper.selectByExample(null);
        return pmsProductSaleAttrs;
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        //添加spu
        pmsProductInfoMapper.insert(pmsProductInfo);
        //添加图片
        List<PmsProductImage> pmsProductImages = pmsProductInfo.getSpuImageList();
        if (pmsProductImages != null && pmsProductImages.size() > 0) {
            for (PmsProductImage pmsProductImage : pmsProductImages) {
                pmsProductImage.setProductId(pmsProductInfo.getId());
                pmsProductImageMapper.insert(pmsProductImage);
            }
        }
        //添加销售属性
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductInfo.getSpuSaleAttrList();
        if (pmsProductSaleAttrs != null && pmsProductSaleAttrs.size() != 0) {
            for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
                pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
                List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttr.getSpuSaleAttrValueList();
                if (pmsProductSaleAttrValues != null && pmsProductSaleAttrValues.size() != 0) {
                    for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttrValues) {
                        pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                        pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
                    }
                }
                pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
            }
        }
        return -1;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId) {
        //查询销售属性
        PmsProductSaleAttrExample pmsProductSaleAttrExample = new PmsProductSaleAttrExample();
        pmsProductSaleAttrExample.createCriteria().andProductIdEqualTo(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectByExample(pmsProductSaleAttrExample);
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            //查询销售属性值
            PmsProductSaleAttrValueExample pmsProductSaleAttrValueExample = new PmsProductSaleAttrValueExample();
            PmsProductSaleAttrValueExample.Criteria criteria = pmsProductSaleAttrValueExample.createCriteria();
            criteria.andProductIdEqualTo(spuId);
            criteria.andSaleAttrIdEqualTo(pmsProductSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectByExample(pmsProductSaleAttrValueExample);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public List<PmsProductImage> spuImageList(Long spuId) {
        PmsProductImageExample pmsProductImageExample=new PmsProductImageExample();
        pmsProductImageExample.createCriteria().andProductIdEqualTo(spuId);
        List<PmsProductImage> pmsProductImages = pmsProductImageMapper.selectByExample(pmsProductImageExample);
        return pmsProductImages;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListIsCheck(Long spuId, Long skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.spuSaleAttrListIsCheck(spuId, skuId);
        return pmsProductSaleAttrs;
    }
}

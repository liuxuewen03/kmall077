package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 16:17
 */
public interface AttrService {
    List<PmsBaseAttrInfo> getPmsBaseAttrInfoList(Integer catalog3Id);

    int add(PmsBaseAttrInfo attrInfo);


    List<PmsBaseAttrValue> getAttrValueList(Integer attrId);
}

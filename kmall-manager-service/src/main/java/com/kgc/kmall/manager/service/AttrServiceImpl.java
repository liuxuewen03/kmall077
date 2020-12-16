package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrInfoExample;
import com.kgc.kmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.kgc.kmall.service.AttrService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 16:19
 */
@Component
@Service
public class AttrServiceImpl implements AttrService {

    @Resource
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;


    @Override
    public List<PmsBaseAttrInfo> getPmsBaseAttrInfoList(Integer catalog3Id) {
        PmsBaseAttrInfoExample pmsBaseAttrInfoExample = new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = pmsBaseAttrInfoExample.createCriteria();
        criteria.andCatalog3IdEqualTo((long) catalog3Id);
        return pmsBaseAttrInfoMapper.selectByExample(pmsBaseAttrInfoExample);
    }
}

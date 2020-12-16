package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.kgc.kmall.manager.mapper.PmsBaseCatalog2Mapper;
import com.kgc.kmall.manager.mapper.PmsBaseCatalog3Mapper;
import com.kgc.kmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 15:18
 */
@Component
@Service
public class CatalogServiceImpl implements CatalogService {

    @Resource
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Resource
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Resource
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getPmsBaseCataLog1() {
        return pmsBaseCatalog1Mapper.selectByExample(null);
    }

    @Override
    public List<PmsBaseCatalog2> getPmsBaseCatalog2(Integer catalog1Id) {
        PmsBaseCatalog2Example pmsBaseCatalog2Example = new PmsBaseCatalog2Example();
        PmsBaseCatalog2Example.Criteria criteria = pmsBaseCatalog2Example.createCriteria();
        criteria.andCatalog1IdEqualTo(catalog1Id);
        return pmsBaseCatalog2Mapper.selectByExample(pmsBaseCatalog2Example);
    }

    @Override
    public List<PmsBaseCatalog3> getPmsBaseCatalog3(Integer catalog2Id) {
        PmsBaseCatalog3Example pmsBaseCatalog3Example = new PmsBaseCatalog3Example();
        PmsBaseCatalog3Example.Criteria criteria = pmsBaseCatalog3Example.createCriteria();
        criteria.andCatalog2IdEqualTo((long) catalog2Id);
        return pmsBaseCatalog3Mapper.selectByExample(pmsBaseCatalog3Example);
    }
}

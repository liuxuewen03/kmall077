package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrInfoExample;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.bean.PmsBaseAttrValueExample;
import com.kgc.kmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.kgc.kmall.manager.mapper.PmsBaseAttrValueMapper;
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
    @Resource
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;


    @Override
    public List<PmsBaseAttrInfo> getPmsBaseAttrInfoList(Integer catalog3Id) {
        PmsBaseAttrInfoExample pmsBaseAttrInfoExample = new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = pmsBaseAttrInfoExample.createCriteria();
        criteria.andCatalog3IdEqualTo((long) catalog3Id);
        return pmsBaseAttrInfoMapper.selectByExample(pmsBaseAttrInfoExample);
    }

    @Override
    public int add(PmsBaseAttrInfo attrInfo) {


        try {
            if (attrInfo.getId() == null) {
                //添加
                pmsBaseAttrInfoMapper.insertSelective(attrInfo);
            } else {
                //修改
                pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(attrInfo);
                //删除
                PmsBaseAttrValueExample pmsBaseAttrValueExample = new PmsBaseAttrValueExample();
                PmsBaseAttrValueExample.Criteria criteria = pmsBaseAttrValueExample.createCriteria();
                criteria.andAttrIdEqualTo(attrInfo.getId());
                pmsBaseAttrValueMapper.deleteByExample(pmsBaseAttrValueExample);
            }
            //添加属性值
            if (attrInfo.getAttrValueList().size() > 0) {
                pmsBaseAttrValueMapper.insertBatch(attrInfo.getId(), attrInfo.getAttrValueList());
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(Integer attrId) {
        PmsBaseAttrValueExample pmsBaseAttrValueExample = new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = pmsBaseAttrValueExample.createCriteria();
        criteria.andAttrIdEqualTo((long) attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(pmsBaseAttrValueExample);
        return pmsBaseAttrValues;
    }
}

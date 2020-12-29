package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 16:22
 */
@RestController
@CrossOrigin
@Api(tags="平台属性",description="有关平台属性的操作")
public class AttrController {
    @Reference
    AttrService attrService;

    @ApiOperation("查询平台属性")
    @RequestMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@ApiParam(name = "catalog3Id",value = "三级分类id")Integer catalog3Id) {
        return attrService.getPmsBaseAttrInfoList(catalog3Id);
    }

    @ApiOperation("添加平台属性")
    @RequestMapping("saveAttrInfo")
    public int saveAttrInfo(@RequestBody @ApiParam(name = "attrInfo",value = "平台属性") PmsBaseAttrInfo attrInfo) {
        int i = attrService.add(attrInfo);
        return i;
    }
    @ApiOperation("查询平台属性值")
    @RequestMapping("getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(@ApiParam(name = "attrId",value = "平台属性id")Integer attrId) {
        List<PmsBaseAttrValue> attrValueList = attrService.getAttrValueList(attrId);
        return attrValueList;
    }

}

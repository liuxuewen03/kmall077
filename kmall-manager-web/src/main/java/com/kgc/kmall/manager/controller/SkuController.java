package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-12-24 8:38
 */
@CrossOrigin
@RestController
@Api(tags = "sku销售属性",description = "sku的操作")
public class SkuController {


    @Reference
    SkuService skuService;

    @ApiOperation("添加sku销售属性")
    @RequestMapping("/saveSkuInfo")
    public String saveSkuInfo(@RequestBody @ApiParam(name = "skuInfo",value = "sku属性") PmsSkuInfo skuInfo) {
        String result = skuService.saveSkuInfo(skuInfo);
        return result;
    }

}

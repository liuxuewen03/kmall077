package com.kgc.kmall.manager.controller;


import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsBaseCatalog2;
import com.kgc.kmall.bean.PmsBaseCatalog3;
import com.kgc.kmall.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 15:30
 */
@CrossOrigin
@RestController
@Api(tags="三级分类",description="三级分类查询")
public class CatalogController {


    @Reference
    CatalogService catalogService;

    @ApiOperation("查询一级分类")
    @RequestMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1(){
        return catalogService.getPmsBaseCataLog1();

    }
    @ApiOperation("查询二级分类")
    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(@ApiParam(name = "catalog1Id",value = "三级分类id")Integer catalog1Id){
        return catalogService.getPmsBaseCatalog2(catalog1Id);
    }
    @ApiOperation("查询三级分类 ")
    @RequestMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(@ApiParam(name = "catalog2Id",value = "二级分类id")Integer catalog2Id){
        return catalogService.getPmsBaseCatalog3(catalog2Id);
    }

}

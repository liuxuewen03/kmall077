package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 14:21
 */
@CrossOrigin
@RestController
@Api(tags = "spu销售属性",description = "spu的操作")
public class SpuController {
    @Reference
    SpuService spuService;

    @Value("${fileServer.url}")
    String fileUrl;


    @ApiOperation("查询spu")
    @RequestMapping("spuList")
    public List<PmsProductInfo> spuList(@ApiParam(name = "catalog3Id",value = "三级分类id")Long catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);

        return pmsProductInfos;
    }

    @ApiOperation("spu文件上传")
    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file")@ApiParam(name = "file",value = "文件") MultipartFile file) {

        try {
            String confFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(confFile);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer, null);

            String orginalFilename = file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(orginalFilename);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            String path = fileUrl;
            for (int i = 0; i < upload_file.length; i++) {
                String s = upload_file[i];
                path += "/" + s;
            }
            System.out.println(path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("查询销售属性")
    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsProductSaleAttrs = spuService.baseSaleAttrList();
        return pmsProductSaleAttrs;
    }

    @ApiOperation("添加spu")
    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody @ApiParam(name = "pmsProductInfo",value = "spu") PmsProductInfo pmsProductInfo) {
        //添加spu
        int i = spuService.saveSpuInfo(pmsProductInfo);
        return i > 0 ? "success" : "fail";
    }

    @ApiOperation("查询销售属性关系")
    @RequestMapping("spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(@ApiParam(name = "spuId",value = "spuId") Long spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    @ApiOperation("查询spu图片")
    @RequestMapping("spuImageList")
    public List<PmsProductImage> spuImageList(@ApiParam(name = "spuId",value = "spuId")Long spuId) {
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }
}

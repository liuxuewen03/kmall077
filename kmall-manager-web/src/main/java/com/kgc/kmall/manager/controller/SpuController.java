package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseSaleAttr;
import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductSaleAttr;
import com.kgc.kmall.service.SpuService;
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
public class SpuController {
    @Reference
    SpuService spuService;

    @Value("${fileServer.url}")
    String fileUrl;

    @RequestMapping("spuList")
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {

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

    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsProductSaleAttrs = spuService.baseSaleAttrList();
        return pmsProductSaleAttrs;
    }

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        return "success";
    }
}

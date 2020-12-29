package com.kgc.kmall.swagger_demo.controller;

import com.kgc.kmall.swagger_demo.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-12-24 15:02
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口",description = "提供用户相关的Rest API")
public class UserController {

    @ApiOperation("新增用户接口")
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public boolean add(@RequestBody User user) {
        return true;
    }

}

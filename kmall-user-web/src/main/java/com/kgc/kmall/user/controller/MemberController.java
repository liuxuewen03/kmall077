package com.kgc.kmall.user.controller;

import com.kgc.kmall.bean.Member;
import com.kgc.kmall.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.MembershipKey;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-15 16:35
 */
@RestController
public class MemberController {


    @Reference
    MemberService memberService;

    @RequestMapping("/test")
    public String test(){
        List<Member> members = memberService.selectAllMember();
        if (members.size()>0){
            return members.toString();
        }else {
            return "没有查到";
        }

    }


}

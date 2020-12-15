package com.kgc.kmall.user.controller;

import com.kgc.kmall.bean.Member;
import com.kgc.kmall.service.MemberService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.channels.MembershipKey;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-15 16:35
 */
@Controller
public class MemberController {


    @Reference
    MemberService memberService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        List<Member> members = memberService.selectAllMember();
        if (members.size()>0){
            return members.toString();
        }else {
            return "没有查到";
        }

    }


}

package com.kgc.kmall.kmallsearchweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shkstart
 * @create 2021-01-04 16:50
 */
@Controller
public class SearchController {



    @RequestMapping("")
    public String index(){

        return "index";
    }

    @RequestMapping("list.html")
    public String list(){

        return "list";
    }

}

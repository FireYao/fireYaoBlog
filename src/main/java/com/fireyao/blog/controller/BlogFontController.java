package com.fireyao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Controller
public class BlogFontController {

    @GetMapping("/")
    public String welcome(){
        //发送登陆的用户信息
        return "index";
    }

}

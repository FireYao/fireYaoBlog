package com.fireyao.blog.controller.admin;

import com.fireyao.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * 后台主界面
 * Created by liuliyuan on 2017/6/19.
 */
@Controller
public class AdminIndexController {
    @Resource
    private BlogService blogService;


    /**
     * 所有博客列表的显示界面.
     * @param page 当前页
     * @param model
     * @return
     */
    @GetMapping("/admin")
    public String blogList(Model model) {
        model.addAttribute("current",1);
        model.addAttribute("pageNum",blogService.adminGetPageNum());
        model.addAttribute("blogList",blogService.getBlogPage(1));
        return "admin/blog_list";
    }

//    @GetMapping("/admin")
    public String monitor(Model model) {
        model.addAttribute("freeMemory", 70);
        return "admin/monitor";
    }
}

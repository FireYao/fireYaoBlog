package com.fireyao.blog.controller;

import com.fireyao.blog.model.BlogView;
import com.fireyao.blog.model.Info;
import com.fireyao.blog.service.BlogService;
import com.fireyao.blog.service.InfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Controller
public class BlogFontController {

    @Resource
    private InfoService infoService;

    @Resource
    private BlogService blogService;

    /**
     * 首页进入文章列表
     * @param model
     * @return
     */
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("info", infoService.getInfo());
        model.addAttribute("archives", blogService.getArchive(1));
        model.addAttribute("pageNum", blogService.getArchiveNum());
        model.addAttribute("current", 1);
        return "archives";
    }


    /**
     * 文章列表
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/archives/{page}")
    public String archives(@PathVariable int page, Model model) {
        model.addAttribute("info", infoService.getInfo());
        model.addAttribute("archives", blogService.getArchive(page));
        model.addAttribute("pageNum", blogService.getArchiveNum());
        model.addAttribute("current", page);
        return "archives";
    }


    /**
     * 文章详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/post/{id}")
    public String post(@PathVariable int id,Model model) {
        model.addAttribute("info",infoService.getInfo());
        BlogView blogView=blogService.getBlog(id);
        BlogView prev=blogService.getPrevBlog(id);
        BlogView next=blogService.getNextBlog(id);
        model.addAttribute("prev",prev);
        model.addAttribute("next",next);
        model.addAttribute("article",blogView.getArticle());
        return "post";
    }

    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("info", infoService.getInfo());
        model.addAttribute("tags", blogService.getTagList());
        return "tags";
    }

    @GetMapping("/tags/{tagName}")
    public String tagName(@PathVariable String tagName, Model model) {
        model.addAttribute("info", infoService.getInfo());
        List<BlogView> views = blogService.getBlogByTag(tagName);
        model.addAttribute("views", views);
        model.addAttribute("tagName", tagName);
        return "tagView";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("avatar", infoService.getInfo().getAvatar());
        String result = request.getParameter("result");
        if (result != null && result.equals("fail")) {
            model.addAttribute("success", 0);
        }
        return "login";
    }

    @PostMapping("/login.action")
    public String doLogin(Info user, HttpServletRequest request) {
        boolean result = infoService.login(user);
        if (result) {
            infoService.addSession(request, user);
            return "redirect:/admin";
        } else {
            return "redirect:/login?result=fail";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        infoService.destroySession(request);
        return "redirect:/login";
    }

}

package com.fireyao.blog.controller.admin;

import com.fireyao.blog.model.BlogView;
import com.fireyao.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * 后台管理中博客管理页面
 * Created by liuliyuan on 2017/6/19.
 */
@Controller
@RequestMapping("/admin")
public class BlogController {


    @Resource
    private BlogService blogService;


    /**
     * 所有博客列表的显示界面.
     * @param page 当前页
     * @param model
     * @return
     */
    @GetMapping("/blogList/{page}")
    public String blogList(@PathVariable int page, Model model) {
        model.addAttribute("current",page);
        model.addAttribute("pageNum",blogService.adminGetPageNum());
        model.addAttribute("blogList",blogService.getBlogPage(page));
        return "admin/blog_list";
    }

    /**
     * 添加博客界面
     * @return
     */
    @GetMapping("/blogAdd")
    public String blogAdd() {
        return "admin/blog_add";
    }

    @PostMapping("/post.action")
    public String postAction(@ModelAttribute("blogForm") BlogView view, Model model){
        blogService.addBlog(view);
        return "redirect:/admin/blogList/1";
    }

    /**
     * 修改博客界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update{id:\\d+}")
    public String blogModify(@PathVariable int id,Model model){
        BlogView blogView=blogService.adminGetBlog(id);
        if (blogView==null){
            return "error";
        }else {
            blogView.setVid(id);
            model.addAttribute("blog",blogView);
            return "admin/blog_modify";
        }
    }

    /**
     * 修改博客
     * @param view
     * @param model
     * @return
     */
    @PostMapping("/update.action")
    public String update(@ModelAttribute("blogForm") BlogView view,Model model){
        blogService.updateBlog(view);
        return "redirect:/post/"+view.getVid();
    }

    /**
     * 删除博客的控制器
     *
     * @param id    要删除的博客id
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,Model model){
        blogService.deleteBlogById(id);
        return "redirect:/admin/blogList/1";
    }


}

package com.fireyao.blog.controller.admin;

import com.fireyao.blog.model.Project;
import com.fireyao.blog.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理中项目页面
 */
@Controller
@RequestMapping("/admin")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    @GetMapping("/project/{page}")
    public String project(@PathVariable int page, Model model){
         model.addAttribute("current",page);
         model.addAttribute("pageNum", projectService.adminGetPageNum());
         model.addAttribute("proList", projectService.adminGetPros(page));
        return "admin/project";
     }
    @PostMapping("/addPro.action")
    public String addProject(Project project){
        projectService.addPro(project);
        return "redirect:/admin/project/1";
    }
    @GetMapping("/deletePro/{id}")
    public String deletePro(@PathVariable int id){
       projectService.deletePro(id);
        return "redirect:/admin/project/1";
    }
    @ResponseBody
    @GetMapping("/pro.json")
    public Project getProJson(HttpServletRequest request){
        String idStr=request.getParameter("id");
        return projectService.getProById(idStr);
    }

    @PostMapping("/updPro.action")
    public String updatePro(@ModelAttribute("updateForm") Project project){
        projectService.updatePro(project);
        return "redirect:/admin/project/1";
    }
}

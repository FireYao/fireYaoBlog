package com.fireyao.blog.controller.admin;

import com.fireyao.blog.model.Info;
import com.fireyao.blog.service.InfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理中个人信息管理页面
 * 此页面中所有处理请求的前缀为/admin，比如info方法匹配的url为/admin/info
 * Created by 火尧 on 2017/6/23.
 */
@Controller
@RequestMapping("/admin")
public class InfoController {

    @Resource
    private InfoService infoService;

    /**
     * 个人信息
     *
     * @param model
     * @return
     */
    @GetMapping("/info")
    public String info(Model model) {
        Info info = infoService.getInfo();
        model.addAttribute("info", info);
        return "admin/info";
    }

    @PostMapping("/info.action")
    public String updateInfo(@ModelAttribute("infoForm") Info info, Model model) {
        boolean result = infoService.updateInfo(info);
        model.addAttribute("targetUrl", "/admin/info");
        if (result) {
            model.addAttribute("result", 1);
            return "admin/result";
        } else {
            model.addAttribute("result", 0);
            return "admin/result";
        }
    }

    @PostMapping("/pass.action")
    public String passModify(@RequestParam String old_pass, @RequestParam String new_pass, HttpServletRequest request) {
        int result = infoService.modifyPw(old_pass, new_pass);
        if (result == 0) {
            infoService.destroySession(request);
        }
        return "redirect:/admin/info?result=" + result;
    }

    @GetMapping("/resume")
    public String resume(Model model) {
        model.addAttribute("md", infoService.getResumeMd());
        return "admin/resume";
    }

    @PostMapping("/resume.action")
    public String resumeUpdate(@ModelAttribute("resumeForm") Info info) {
        infoService.updateResume(info);
        return "redirect:/admin/resume";
    }
}

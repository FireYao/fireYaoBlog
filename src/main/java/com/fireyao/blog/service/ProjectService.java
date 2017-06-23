package com.fireyao.blog.service;

import com.fireyao.blog.model.Project;

import java.util.List;

/**
 * Created by 火尧 on 2017/6/23.
 */
public interface ProjectService {
    List<Project> getPros(int page);//前端project页面获取项目列表

    void addPro(Project project);//后台管理添加项目

    List<Project> adminGetPros(int page);//后台管理中获取项目列表

    int adminGetPageNum();//后台管理中获取项目页面总数量

    int getPageNum();//前端获取项目页面总数

    void deletePro(int id);//后台管理删除项目

    Project getProById(String idStr);//后台管理获取项目所有信息

    void updatePro(Project project);//后台管理更新博客信息
}

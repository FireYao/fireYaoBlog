package com.fireyao.blog.service.impl;

import com.fireyao.blog.mapping.ProjectMapper;
import com.fireyao.blog.model.Project;
import com.fireyao.blog.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 火尧 on 2017/6/23.
 */
@Service
public class ProjectSrerviceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getPros(int page) {
        int start = (page - 1) * 5;
        return projectMapper.select(start);
    }

    @Override
    public void addPro(Project project) {
        Date date = new Date();
        project.setDate(date);
        projectMapper.insert(project);
    }

    @Override
    public List<Project> adminGetPros(int page) {
        int start = (page - 1) * 10;
        return projectMapper.adminSelect(start);
    }


    @Override
    public int adminGetPageNum() {
        int count = projectMapper.count();
        return count % 10 == 0 ? count / 10 : count / 10 + 1;
    }

    @Override
    public int getPageNum() {
        int count = projectMapper.count();
        return count % 5 == 0 ? count / 5 : count / 5 + 1;
    }

    @Override
    public void deletePro(int id) {
        projectMapper.delete(id);
    }

    @Override
    public Project getProById(String idStr) {
        return projectMapper.selectById(Integer.valueOf(idStr));
    }

    @Override
    public void updatePro(Project project) {
        projectMapper.update(project);
    }
}

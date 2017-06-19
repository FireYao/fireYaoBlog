package com.fireyao.blog.service;

import com.fireyao.blog.model.Info;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuliyuan on 2017/6/19.
 */
public interface InfoService {

    Info getInfo();//获取username,email,github,twitter,avatar信息

    boolean login(Info info);//登录验证

    int modifyPw(String oldPass, String newPass);//修改密码

    boolean updateInfo(Info info);//后台管理中更新sername,email,github,twitter,avatar信息

    boolean checkPass(String oldPass);//修改密码之前进行的密码验证

    void addSession(HttpServletRequest request, Info info);//登录成功后添加session

    void destroySession(HttpServletRequest request);//退出登录或者超时之后销毁session

    String getResumeMd();//后台管理中获取简历的markdown文本

    boolean updateResume(Info info);//后台管理更新简历

    String getResumeView();//前端about页面获取简历的html文本

    void updateAvatar(String avatar);//更新头像

}

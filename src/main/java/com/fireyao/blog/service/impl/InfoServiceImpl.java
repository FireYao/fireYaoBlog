package com.fireyao.blog.service.impl;

import com.fireyao.blog.mapping.InfoMapper;
import com.fireyao.blog.model.Info;
import com.fireyao.blog.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Service
public class InfoServiceImpl implements InfoService {

    private static final int MODIFYPASSSUC = 0;//修改密码成功
    private static final int PASSERROE = 1;//密码错误
    private static final int SySTEMERROE = 2;//系统错误
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Resource
    private InfoMapper infoMapper;

    @Override
//    @Cacheable(value = "profileOfInfo",key = "1")
    public Info getInfo() {
        return infoMapper.selectInfo();
    }

    @Override
    public boolean login(Info info) {
        int num = 0;
        try {
            num = infoMapper.selectByPw(info.getUsername(), info.getPassword());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0 ? true : false;
    }

    @Override
    public int modifyPw(String oldPass, String newPass) {
        int result;
        if (checkPass(oldPass)) {
            try {
                infoMapper.updataPass(newPass);
                result = MODIFYPASSSUC;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                result = SySTEMERROE;
            }
        } else {
            result = PASSERROE;
        }
        return result;
    }

    @Override
    public boolean updateInfo(Info info) {
        boolean flag = true;
        try {
            infoMapper.updateInfo(info);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean checkPass(String oldPass) {
        return infoMapper.selectByOldPass(oldPass) > 0;
    }

    @Override
    public void addSession(HttpServletRequest request, Info info) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cur_user",info);
        session.setMaxInactiveInterval(600);
    }

    @Override
    public void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("cur_user");
    }

    @Override
    public String getResumeMd() {
        String md="";
        try {
            md=infoMapper.selectMd();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return md;
    }

    @Override
    public boolean updateResume(Info info) {
        boolean result=true;
        try {
            infoMapper.updateInfo(info);
        } catch (Exception e) {
            result=false;
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public String getResumeView() {
        return infoMapper.selectResume();
    }

    @Override
    public void updateAvatar(String avatar) {
        infoMapper.updateAvater(avatar);
    }
}

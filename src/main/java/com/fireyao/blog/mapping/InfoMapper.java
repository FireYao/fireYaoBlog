package com.fireyao.blog.mapping;

import com.fireyao.blog.model.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Repository
@Mapper
public interface InfoMapper {
    Info selectInfo() throws RuntimeException;

    int selectByPw(@Param("username") String username, @Param("password") String password) throws RuntimeException;

    int selectByOldPass(@Param("password") String oldPass) throws RuntimeException;

    String selectMd() throws RuntimeException;

    String selectResume() throws RuntimeException;

    void updateInfo(Info info) throws RuntimeException;

    void updataPass(@Param("newPass") String newPass) throws RuntimeException;

    void updateAvater(@Param("avatar") String avatar) throws RuntimeException;

}

package com.fireyao.blog.model;

/**
 * Created by liuliyuan on 2017/6/19.
 */

import lombok.Builder;
import lombok.Getter;

/**
 * 全局配置类，用于储存自定义的全局变量
 */
@Builder
public class SystemSetting {
    @Getter
    private String picHome;
}

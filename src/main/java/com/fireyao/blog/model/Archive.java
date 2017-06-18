package com.fireyao.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 前台Archive模块展示的博客列表
 * Created by liuliyuan on 2017/6/19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Archive {
    private int year;//年份
    private List<BlogView> list;//此年份的博客列表
}
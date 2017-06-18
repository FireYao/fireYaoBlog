package com.fireyao.blog.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 博客信息
 * Created by liuliyuan on 2017/6/19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BlogView {

    private Integer vid;//id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;//博客创建日期
    private String title;//博客标题，不可为空
    private String article;//博客内容的html文本
    private String tags;//标签，不同标签以,隔开
    private String md;//博客内容的markdown文本
    private String monthDay;//形如"Oct 04",为了方便archives页面显示，并不对应数据库的任何一列

    public BlogView(String title,String tags,String md){
        this.title=title;
        this.tags=tags;
        this.md=md;
    }
    public BlogView(int vid,String title,String tags){
        this.vid=vid;
        this.title=title;
        this.tags=tags;
    }

}

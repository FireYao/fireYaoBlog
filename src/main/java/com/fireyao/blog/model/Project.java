package com.fireyao.blog.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    private int id;//id
    private String name;//项目名称
    private String url;//项目url地址，例如https://github.com/jcalaz/jcalaBlog
    private String tech;//项目所用技术
    private String desp;//项目描述
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;//项目创建时间

}

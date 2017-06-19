package com.fireyao.blog.config;

import com.fireyao.blog.interceptor.UserSecurityInterceptor;
import com.fireyao.blog.model.SystemSetting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * web配置类 在此处注册拦截器
 * Created by liuliyuan on 2017/6/19.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${pic.home}")
    private String picHome;

    @Resource
    private UserSecurityInterceptor userSecurityInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册后台登陆拦截器
        registry.addInterceptor(userSecurityInterceptor).addPathPatterns("/admin/**");
    }

    /**
     * 注册过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST","DELETE"));
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    /*@Bean
    public SystemSetting systemSetting(){
        return SystemSetting.builder()
                .picHome(picHome)
                .build();
    }*/

}

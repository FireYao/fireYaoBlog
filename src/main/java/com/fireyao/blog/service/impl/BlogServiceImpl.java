package com.fireyao.blog.service.impl;

import com.fireyao.blog.mapping.BlogMapper;
import com.fireyao.blog.model.Archive;
import com.fireyao.blog.model.BlogView;
import com.fireyao.blog.service.BlogService;
import com.fireyao.blog.util.TimeTools;
import com.fireyao.blog.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by liuliyuan on 2017/6/19.
 */
@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addBlog(BlogView blogView) {
        blogView.setDate(new Date(System.currentTimeMillis()));
        blogMapper.insertBlog(blogView);
        addViewTag(blogView.getTags(),blogView.getVid());
    }

    @Override
    public BlogView adminGetBlog(int vid) {

        BlogView blogView = blogMapper.selectAdmin(vid);

        return blogView;
    }

    @Override
    public List<BlogView> getBlogPage(int id) {
        int start = (id - 1) * 10;
        return blogMapper.selectTenBlogs(start);
    }

    @Override
    public int adminGetPageNum() {
        int num = blogMapper.selectBlogCount();
        return num % 10 == 0 ? num / 10 : num / 10 + 1;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateBlog(BlogView blogView) {
        blogMapper.updateBlogView(blogView);
        updateViewTag(blogView.getTags(),blogView.getVid());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteBlogById(int vid) {
        blogMapper.deleteBlogView(vid);
    }

    @Override
    public List<String> getTagList() {
        return blogMapper.selectTags();
    }

    @Override
    public List<Archive> getArchive(int page) {
        int start = (page - 1) * 10;
        return bv2Ar(blogMapper.selectArc(start, 10));
    }

    @Override
    public int getArchiveNum() {
        int blogNum = blogMapper.selectBlogCount();
        return blogNum % 10 == 0 ? blogNum / 10 : blogNum / 10 + 1;
    }

    @Override
    public BlogView getBlog(int vid) {
        return blogMapper.selectView(vid);
    }

    @Override
    public BlogView getPrevBlog(int vid) {
        return blogMapper.selectPreView(vid);
    }

    @Override
    public BlogView getNextBlog(int vid) {
        BlogView blogView = null;
        try {
            blogView = blogMapper.selectNextView(vid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return blogView;
    }

    @Override
    public List<BlogView> getBlogByTag(String tagName) {
        List<BlogView> views = new ArrayList<>();
        List<Integer> vids = blogMapper.selectVidBytag(tagName);
        for (int vid : vids) {
            BlogView view = blogMapper.selectTagView(vid);
            if (view != null) {
                view.setVid(vid);
                String monthDay = TimeTools.getEdate(view.getDate());
                view.setMonthDay(monthDay);
                views.add(view);
            }
        }
        return views;
    }

        /**
         * 按年份分类博客
         *
         * @param views
         * @return
         */
        private List<Archive> bv2Ar (List < BlogView > views) {
            List<Archive> archives = new ArrayList<>();
            Map<Integer, Archive> years2Ar = new HashMap<>();
            for (BlogView view : views) {
                Date date = view.getDate();

                view.setMonthDay(TimeTools.getEdate(date));
                int year = TimeTools.getYear(date);
                if (years2Ar.containsKey(year)) {
                    years2Ar.get(year).getList().add(view);
                } else {
                    List<BlogView> vlew_list = new ArrayList<>();
                    Archive archive = new Archive(year, vlew_list);
                    years2Ar.put(year, archive);
                    archive.getList().add(view);
                    archives.add(archive);
                }
            }
            return archives;
        }

    private void addViewTag(String tagStr,int vid){
        List<String> tagList= Tools.getTagList(tagStr);
        for (String tag:tagList){
            blogMapper.insertViewTag(tag,vid);
        }

    }
    private void updateViewTag(String tagStr,int vid){
        blogMapper.deleteViewTag(vid);
        List<String> tagList=Tools.getTagList(tagStr);
        for (String tag:tagList){
            blogMapper.insertViewTag(tag,vid);
        }

    }

    }

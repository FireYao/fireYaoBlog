package com.fireyao.blog.mapping;

import com.fireyao.blog.model.BlogView;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 映射所有博客操作的sql语句
 * Created by liuliyuan on 2017/6/19.
 */
@Mapper
@Repository
public interface BlogMapper {

    /**
     * 前台查询博客列表
     * 每页7条
     *
     * @param start
     * @return
     */
    @Select({
            "select vid,title,tags",
            "from blog_view",
            "limit #{start},10"
    })
    List<BlogView> selectTenBlogs(@Param("start") int start);

    @Select("select count(1) from blog_view")
    int selectBlogCount();

    @Select("select distinct name from view_tag")
    @ResultType(String.class)
    List<String> selectTags();

    /**
     * 后台查询博客列表
     * 每页10条
     *
     * @param start
     * @return
     */
    @Select({
            "select vid,date,title",
            "from blog_view",
            "order by date desc",
            "limit #{start},#{pageSize}"
    })
    List<BlogView> selectArc(@Param("start") int start, @Param("pageSize") int pageSize);


    @Select({
            "select title,tags,md",
            "from blog_view",
            "where vid = #{id}",
            "limit 1"
    })
    BlogView selectAdmin(@Param("id") int id);


    @Select({
            "select title,article",
            "from blog_view",
            "where vid = #{id}",
            "limit 1"
    })
    BlogView selectView(@Param("id") int id);

    /**
     * 查询前一篇博客
     *
     * @param vid
     * @return
     */
    @Select({
            "select vid,title ",
            "from blog_view",
            "where vid < #{id}",
            "order by vid desc",
            "limit 1"
    })
    BlogView selectPreView(@Param("id") int vid);

    /**
     * 后一篇博客
     *
     * @param vid
     * @return
     */
    @Select({
            "select vid,title ",
            "from blog_view",
            "where vid > #{id}",
            "limit 1"
    })
    BlogView selectNextView(@Param("id") int vid);

    /**
     * 标签查询博客
     *
     * @param tagName
     * @return
     */
    @Select({
            "select distinct vid",
            "from view_tag",
            "where name = #{tag}"
    })
    List<Integer> selectVidBytag(@Param("tag") String tagName);


    /**
     * 某一博客的标签
     *
     * @param vid
     * @return
     */
    @Select({
            "select date,title",
            "from blog_view",
            "where vid = #{vid}",
            "limit 1"
    })
    BlogView selectTagView(@Param("vid") int vid);

    /**
     * 新增博客
     * 返回新增博客的id
     *
     * @param blogView
     * @return
     */
    @Insert({"insert into blog_view ",
            "(date,title,article,tags,md) ",
            "values(#{bv.date},#{bv.title},",
            "#{bv.article},#{bv.tags},#{bv.md})"})
    @SelectKey(before = false, keyProperty = "bv.vid", resultType = Integer.class,
            statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    int insertBlog(@Param("bv") BlogView blogView);

    /**
     * 插入标签
     *
     * @param tagName
     * @param vid
     * @return
     */
    @Insert("insert ignore into view_tag (name,vid) values(#{tn},#{id})")
    int insertViewTag(@Param("tn") String tagName, @Param("id") int vid);

    /**
     * 删除标签
     *
     * @param vid
     * @return
     */
    @Delete("delete from view_tag where vid = #{vid}")
    int deleteViewTag(@Param("vid") int vid);

    /**
     * 删除标签
     *
     * @param vid
     * @return
     */
    @Delete("delete from blog_view where vid =#{vid} limit 1")
    int deleteBlogView(@Param("vid") int vid);

    /**
     * 更新博客
     *
     * @param blogView
     */
    @Update({
            "update blog_view",
            "set title = #{bv.title},",
            "tags = #{bv.tags},",
            "md = #{bv.md},",
            "article = #{bv.article}",
            "where vid = #{bv.vid}"
    })
    void updateBlogView(@Param("bv") BlogView blogView);

}

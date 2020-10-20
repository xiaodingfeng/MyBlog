package com.xiaobai.blog.Dao;

import com.xiaobai.blog.bean.Content;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Mapper
@Component
public interface contentIml {
    @Insert("insert into Content(title,category,textContent,imageUri,time) " +
            "values(#{title},#{category},#{textContent},#{imageUri},#{time})  ")
    @Options(useGeneratedKeys = true,keyProperty = "iD")
    Integer insertcontent(Content content);
    @Delete("delete from Content where iD=#{id}")
    Boolean deletecontent(Integer id);
    @Select("<script>"+
            "SELECT Content.*,Category.categoryName " +
            "FROM Content LEFT JOIN Category ON Content.category=Category.ID "+
            "order by time desc <if test='size!=null'>  limit #{page},#{size} </if>"+
             "</script>")
    Collection<Content> getallcontent(Integer page,Integer size);

    @Select("SELECT Content.*,Category.categoryName FROM Content LEFT JOIN Category ON Content.category=Category.ID where Content.ID=#{id}")
    Content getcontent(Integer id);
    @Select("select Content.*,Category.categoryName FROM Content LEFT JOIN Category ON Content.category=Category.ID where Content.title like concat('%',#{str},'%') " +
            "or Content.textContent like concat('%',#{str},'%')"+
            "or Content.time like concat('%',#{str},'%') or Category.categoryName like concat('%',#{str},'%') order by time desc ")
    Collection<Content> getsearchcontent(String str);

    @Select("select ID,title,time from Content where isTop=1")
    Collection<Content> gettopcontent();

    @Select("select * from Content where category=#{id}")
    Collection<Content> getcontentbyCate(Integer id);
}

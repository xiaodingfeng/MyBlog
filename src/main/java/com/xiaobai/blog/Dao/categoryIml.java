package com.xiaobai.blog.Dao;

import com.xiaobai.blog.bean.Category;
import com.xiaobai.blog.bean.Content;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Mapper
@Component
public interface categoryIml {
    @Insert("insert into Category(categoryName)" +
            "values(#{categoryName})  ")
    @Options(useGeneratedKeys = true,keyProperty = "iD")
    Boolean insertCategory(Category category);
    @Delete("delete from Category where id=#{id}")
    Boolean deleteCategory(Integer id);
    @Select("SELECT * FROM Category")
    Collection<Category> getallCategory();

    @Select("select ID from Category where categoryName=#{categoryName}")
    Integer select(String categoryName);
    @Delete("delete from Content where category=#{id}")
    Integer deleteAll(Integer id);
}

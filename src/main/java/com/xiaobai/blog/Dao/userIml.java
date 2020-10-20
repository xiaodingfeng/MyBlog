package com.xiaobai.blog.Dao;

import com.xiaobai.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Mapper
@Component
public interface userIml {
    @Select("select * from User")
    Collection<User> getUser();
    @Select("select birth from User where id=1")
    String getBirth();
}

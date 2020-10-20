package com.xiaobai.blog.service;

import com.xiaobai.blog.Dao.userIml;
import com.xiaobai.blog.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class userService {
    @Autowired
    userIml userIml;
    public Collection<User> getUser(){
        return userIml.getUser();
    }
    public String getBirth(){
        return userIml.getBirth();
    }
}

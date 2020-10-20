package com.xiaobai.blog.service;

import com.xiaobai.blog.Dao.contentIml;
import com.xiaobai.blog.bean.Content;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class contentService{
    @Autowired
    contentIml contentIml;
    @Autowired
    RedisTemplate redisTemplate;
    public Integer insertcontent(Content content){
        redisTemplate.delete("content_All_All");
        redisTemplate.delete("content_All_10");
        redisTemplate.delete("content_bycateid_"+content.getCategory());
        return contentIml.insertcontent(content);
    }
    public Boolean deletecontent(Integer id){
        redisTemplate.delete("content_All_All");
        redisTemplate.delete("content_All_10");
        redisTemplate.delete("content_byid_"+id);
        return contentIml.deletecontent(id);
    }
    public Collection<Content> getallcontent(Integer page,Integer size){
        String key="";
        if (size==null){
            key="content_All_All";
        }
        else {
            key="content_All_10";
        }
        ValueOperations<String, Collection<Content>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<Content> contents = operations.get(key);
            return contents;
        } else {
            Collection<Content> contents = contentIml.getallcontent(page,size);
            // 写入缓存
            operations.set(key, contents, 30, TimeUnit.DAYS);
            return contents;
        }
    }
    public Content getcontent(Integer id){
        String key = "content_byid_"+id;
        ValueOperations<String, Content> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Content contents = operations.get(key);
            return contents;
        } else {
            Content contents = contentIml.getcontent(id);
            // 写入缓存
            operations.set(key, contents, 30, TimeUnit.DAYS);
            return contents;
        }
    }
    public Collection<Content> getsearchcontent(String str){
        return contentIml.getsearchcontent(str);
    }
    public Collection<Content> gettopcontent(){
        String key = "content_byistop";
        ValueOperations<String, Collection<Content>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<Content> contents = operations.get(key);
            return contents;
        } else {
            Collection<Content> contents = contentIml.gettopcontent();
            // 写入缓存
            operations.set(key, contents, 10, TimeUnit.DAYS);
            return contents;
        }
    }
    public Collection<Content> getcontentbyCate(Integer id){
        String key = "content_bycateid_"+id;
        ValueOperations<String, Collection<Content>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<Content> contents = operations.get(key);
            return contents;
        } else {
            Collection<Content> contents = contentIml.getcontentbyCate(id);
            // 写入缓存
            operations.set(key, contents, 30, TimeUnit.DAYS);
            return contents;
        }
    }
}

package com.xiaobai.blog.service;

import com.xiaobai.blog.Dao.categoryIml;
import com.xiaobai.blog.bean.Category;
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
public class categoryService {
    @Autowired
    categoryIml categoryIml;
    @Autowired
    RedisTemplate redisTemplate;
    public Boolean insertCategory(Category category){
        redisTemplate.delete("categoryall");
        return categoryIml.insertCategory(category);
    }

    public Boolean deleteCategory(Integer id){
        redisTemplate.delete("categoryall");
        return categoryIml.deleteCategory(id);
    }

    public Collection<Category> getallCategory(){
        String key = "categoryall";
        ValueOperations<String, Collection<Category>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<Category> categories = operations.get(key);
            return categories;
        } else {
            Collection<Category> categories = categoryIml.getallCategory();
            // 写入缓存
            operations.set(key, categories, 30, TimeUnit.DAYS);
            return categories;
        }
    }
    public Integer select(String categoryName){
        return categoryIml.select(categoryName);
    }
    public Integer deleteAll(Integer id){
        redisTemplate.delete("content_All_All");
        redisTemplate.delete("content_All_10");
        return categoryIml.deleteAll(id);
    }
}

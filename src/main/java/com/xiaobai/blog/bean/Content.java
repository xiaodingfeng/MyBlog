package com.xiaobai.blog.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Xiaobai
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private Integer iD;
    private String title;
    private Integer category;
    private String categoryName;
    private String textContent;
    private String imageUri;
    private Date time;
}

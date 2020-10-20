package com.xiaobai.blog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @author Xiaobai
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer iD;
    private String categoryName;
}

package com.xiaobai.blog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YellowCalendar {
    /**
     * 公历
     */
    private String gregorianCalendar;
    /**
     * 农历
     */
    private String lunarCalendar;
    /**
     * 宜
     */
    private HashSet<String> should;
    /**
     * 忌
     */
    private HashSet<String> avoid;
    /**
     * 建议
     */
    private String proposal;
}

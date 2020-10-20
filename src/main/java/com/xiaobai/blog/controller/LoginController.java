package com.xiaobai.blog.controller;

import com.xiaobai.blog.bean.YellowCalendar;
import com.xiaobai.blog.bean.Category;
import com.xiaobai.blog.bean.Content;
import com.xiaobai.blog.service.categoryService;
import com.xiaobai.blog.service.contentService;
import com.xiaobai.blog.service.userService;
import com.xiaobai.blog.staticlass.UrlClass;
import com.xiaobai.blog.until.JsoupUntils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.LazyContextVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    contentService contentService;
    @Autowired
    categoryService categoryService;
    @Autowired
    userService userService;
    @Autowired
    JsoupUntils jsoupUntils;
    @GetMapping("/")
    public String main(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String re=request.getParameter("id");
        model.addAttribute("textContentAll", new LazyContextVariable() {
            @Override
            protected Object loadValue() {
                Collection<Content> textContent=null;
                if (re==null) {
                    textContent=contentService.getallcontent(0,null);
                }
                else {
                    textContent= contentService.getcontentbyCate(Integer.valueOf(re));
                }
                List<Content> contents = new ArrayList<>();
                for (Content content : textContent) {
                    Document parse = Jsoup.parse(content.getTextContent());
                    content.setTextContent(parse.text().substring(0,Math.min(200,parse.text().length())));
                    contents.add(content);
                }
                return contents;
            }
        });
        model.addAttribute("textContent", new LazyContextVariable() {
            @Override
            protected Object loadValue() {
                return contentService.gettopcontent();
            }
        });

        model.addAttribute("category", new LazyContextVariable() {
            @Override
            protected Object loadValue() {
                return categoryService.getallCategory();
            }
        });
        return "main/index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}

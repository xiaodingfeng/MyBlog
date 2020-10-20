package com.xiaobai.blog.config;

import com.xiaobai.blog.bean.User;
import com.xiaobai.blog.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    userService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
       .antMatchers("/login")
                .permitAll()
                .antMatchers("/edit")
                .hasRole("ADMIN");
        http.formLogin()
                .loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/");
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().alwaysRemember(true);
    }
    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // 重写方法，自定义用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        Collection<User> user = userService.getUser();
//        for (User user1 : user) {
            auth.inMemoryAuthentication().withUser("虫虫").password(new BCryptPasswordEncoder().encode("153716")).roles("ADMIN","USER");
//        }

    }

}

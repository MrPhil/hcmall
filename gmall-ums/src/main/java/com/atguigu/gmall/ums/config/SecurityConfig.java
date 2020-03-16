package com.atguigu.gmall.ums.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author rzhstart
 * @create 2020 - 03 - 07 - 19:11
 */

//这个文件用于跳过逆向工程自动生成的登陆界面

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.csrf().disable();
    }

}

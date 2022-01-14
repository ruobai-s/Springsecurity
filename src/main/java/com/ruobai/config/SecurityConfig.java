package com.ruobai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("user").password(new
                MyPasswordEncoder().encode("000")).roles("USER")
                .and()
                .withUser("admin").password(new
                MyPasswordEncoder().encode("123")).roles("ADMIN","USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        admin:index and html 页面
        user:index 页面
        23:login 登录设置
         */
        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/index").access("hasRole('ADMIN') or hasRole('USER')")
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .and()
                        .logout()
                        .permitAll()
                        .and()
                        .csrf()
                        .disable();

    }
}

package com.springsecurity.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigTest {

    //注入数据源
    @Autowired
    private DataSource dataSource;
    //配置对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //配置注销页面地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
        //配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin() //自定义登录页面
                .loginPage("/login.html")  //设置登录页面
                .loginProcessingUrl("/user/login")  //登录访问路径
                .defaultSuccessUrl("/success.html").permitAll()  //登录成功后跳转路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll()  //设置哪些路径可以直接访问，不需要认证
                //当前登录用户只有具有admins权限才可以访问这个路径
                //1.hasAuthority方法
                //.antMatchers("/test/index").hasAuthority("admins")
                //2.hasAnyAuthority方法
                //.antMatchers("/test/index").hasAnyAuthority("admins","manager")
                //3.hasRole方法
                .antMatchers("/test/index").hasRole("sale")
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(600)//设置有效时常
                .and().csrf().disable();  //关闭csrf防护
        return http.build();
    }
}

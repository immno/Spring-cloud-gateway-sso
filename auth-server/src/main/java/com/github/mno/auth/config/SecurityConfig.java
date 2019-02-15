package com.github.mno.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Spring-cloud-gateway-sso
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints,同时主要作用于用户的登录(form login,Basic auth)
 *
 * @author mno
 * @date 2019/2/11 16:51
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("user_mno")
                .password(passwordEncoder.encode("123456")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .csrf().disable();
        // 没有配置 authorizeRequests 是因为，我想让所有的请求都携带 token，而不是携带 session，所以就在 OauthResourceConfig 中配置了
    }

}
package com.github.mno.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:16
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/uaa/oauth/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/uaa/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        http.httpBasic().disable();
    }
}

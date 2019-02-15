package com.github.mno.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * EasySeries-oauth2
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
 *
 * @author mno
 * @date 2018/2/24 8:49
 */
@Configuration
@EnableResourceServer
public class OauthResourceConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth2";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable();
        http.requestMatchers().antMatchers("/oauth/**").anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}

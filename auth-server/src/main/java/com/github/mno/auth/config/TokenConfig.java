package com.github.mno.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/12 16:22
 */
@Configuration
public class TokenConfig {

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        //access_token存活1个小时
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
        //refresh_token存活24个小时
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(24));
        // false:只能刷新 一次 token
        tokenServices.setReuseRefreshToken(true);
        // 使用内存存储
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

}

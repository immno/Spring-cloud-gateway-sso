package com.github.mno.auth.config;

import com.github.mno.auth.exts.CustomWebResponseExceptionTranslator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:51
 */
@Configuration
@EnableAuthorizationServer
public class OauthAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private AuthorizationServerTokenServices tokenServices;

    /**
     * （1）scope：表示权限范围，可选项，用户授权页面时进行选择
     * <p>
     * （2）authorizedGrantTypes：有四种授权方式 
     * <p>
     * Authorization Code：用验证获取code，再用code去获取token（用的最多的方式，也是最安全的方式）
     * Implicit: 隐式授权模式
     * Client Credentials (用來取得 App Access Token)
     * Resource Owner Password Credentials
     * （3）authorities：授予client的权限
     *
     * @see org.springframework.security.oauth2.provider.client.BaseClientDetails
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // oauth_client_details
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("app")
                .redirectUris("http://www.baidu.com")
                // 是否自动授权
                .autoApprove(true)
                .and()
                .withClient("clientIdPassword")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")
                .scopes("read")
                .redirectUris("http://www.baidu.com")
                // 是否自动授权
                .autoApprove(false);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //oauth/check_token 开启
        oauthServer.checkTokenAccess("isAuthenticated()");
        oauthServer.tokenKeyAccess("permitAll()");
        //允许 &client_id=oauth_client&client_secret=oauth_client_secret
        oauthServer.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // oauth_approvals
                .approvalStore(new InMemoryApprovalStore())
                // oauth_code
                .authorizationCodeServices(new InMemoryAuthorizationCodeServices())
                .tokenStore(tokenStore)
                .tokenServices(tokenServices)
                .exceptionTranslator(new CustomWebResponseExceptionTranslator())
                .authenticationManager(authenticationManager);
    }

}
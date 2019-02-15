package com.github.mno.auth.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/12 19:37
 */
//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Resource
    private UserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        LOGGER.debug("## username: {}", username);
        //模拟验证。。。并从数据库找到的用户
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        //数据库用户的密码
        String password = userDetails.getPassword();
        //授权
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}

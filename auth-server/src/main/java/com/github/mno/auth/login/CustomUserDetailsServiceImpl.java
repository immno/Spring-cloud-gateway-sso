package com.github.mno.auth.login;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/12 19:44
 */
//@Component("customUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, username + "AAA", Collections.emptyList());
    }
}
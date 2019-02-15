package com.github.mno.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:48
 */
@RestController
public class OauthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

    @GetMapping("/deny/user")
    public Principal user(Principal user) {
        return user;
    }

}

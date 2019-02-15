package com.github.mno.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:47
 */
@Controller
public class AuthorizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}

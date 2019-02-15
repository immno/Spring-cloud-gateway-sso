package com.github.mno.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:47
 */
@Controller
public class AuthorizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    @RequestMapping("/oauth/login")
    public String getAccessConfirmation() {
        return "login";
    }

    /**
     * 自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
     *
     * @param model   model
     * @param request request
     * @return String
     */
    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        model.put("scopeList", scopeList);
        return "grant";
    }
}

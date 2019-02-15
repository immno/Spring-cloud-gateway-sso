package com.github.mno.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Spring-cloud-gateway-sso
 *
 * @author mno
 * @date 2019/2/11 16:36
 */
@SpringBootApplication
@EnableEurekaClient
public class Oauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }

    @Bean
    LoadBalancerInterceptor loadBalancerInterceptor(LoadBalancerClient loadBalance) {
        return new LoadBalancerInterceptor(loadBalance);
    }
}

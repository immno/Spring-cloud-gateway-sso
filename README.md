# Spring-cloud-gateway-sso
An example of spring oauth2 authorization and resource server using Token.**Based on Finchley.SR2（Oauth 2.X）.** 
[官方文档](https://cloud.spring.io/spring-cloud-static/Finchley.SR2/)

## 使用
所有请求都通过 `api-gateway`，包括`用户认证`，主要涉及三个方面：
- 授权登录（oauth/authorize）  
![配置](img/authorize_cofig.png)  
请求[http://localhost:8001/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
](http://localhost:8001/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com)  
如果没有登录，会跳转到登录页面（`/oauth/login`）


## Eureka-server:服务发现
1. 为了安全设置了密码，基于`HttpBasic`认证（mno:immno），在`application.yml`设置
2. `Finchley.SR2`版本特性，需要添加一个`WebSecurityConfigurerAdapter`配置,[官方说明](https://cloud.spring.io/spring-cloud-static/Finchley.SR2/multi/multi_spring-cloud-eureka-server.html#_securing_the_eureka_server)
```java
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
```
### Oauth操作（全程使用Token，不使用Session）
1. 获取`token`（携带请求头客户端凭证：`Authorization: Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=`，`new String(Base64Utils.encode(("clientIdPassword:secret").getBytes()))`）
POST http://localhost:8001/oauth/token
```json
{
"access_token": "e175f33b-e071-45d0-8819-284c7dd4e376",
"token_type": "bearer",
"refresh_token": "cd9d32c0-dfbf-42fe-b0b2-7ce48d237ef2",
"expires_in": 3599,
"scope": "read"
}
```
2. 获取用户信息
GET http://localhost:8001/deny/user
Authorization: Bearer e175f33b-e071-45d0-8819-284c7dd4e376
```json
{"authorities":[{"authority": "ROLE_USER" } ], "details":{"remoteAddress": "0:0:0:0:0:0:0:1",…}
```
3. 使用`access_token`授权`/oauth/authorize`(这样就不用使用`Session`，可以全程使用`token`了)
POST http://localhost:8001/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
Authorization: Bearer e175f33b-e071-45d0-8819-284c7dd4e376
```
// response headers
Location:	http://www.baidu.com?code=94B3XO
```
## 问题
首先理解：
- WebSecurityConfigurerAdapter  
用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)  
- ResourceServerConfigurerAdapter  
用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)

出现问题，请先打开日志级别:
```yaml
logging:
  level:
    org.springframework.security: debug
```
1. Could not fetch user details: class org.springframework.web.client.HttpClientErrorException, 401 null  
一般来说有两种情况：
- 没有`OAuth2AuthenticationProcessingFilter`过滤器
- `OAuth2AuthenticationProcessingFilter`过滤器顺序放置不对
可参照下面第2点解决
2.  Access is denied (user is anonymous); redirecting to authentication entry point
Debug查看`org.springframework.security.web.FilterChainProxy#doFilter`中的过滤器及顺序
也是因为没有`OAuth2AuthenticationProcessingFilter`,添加`@EnableResourceServer`注解，就会自动加入过滤器
3. 会有一个奇怪的现象当在请求`/oauth/token`时，`OAuth2AuthenticationProcessingFilter`顺序是正确的，
但是携带`token`请求`/oauth/authorize`时
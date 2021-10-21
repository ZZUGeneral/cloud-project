package top.yhl.cloud.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OauthServerConfig {

    public static final String RESOURCE_ID = "sos-resource";

    // 客户端 ID
    public static final String CLIENT_ID = "sos-client";
    // 客户端秘钥
    public static final String CLIENT_SECRET = "sos-client-secret";
    public static final String ROLE_ADMIN = "ADMIN";
    // 鉴权模式
    public static final String[] GRANT_TYPE = {"password", "refresh_token"};


    /**
     * OAuth2不定义ResourceServer，认证鉴权采用方式为：
     * 客户端请求并授权后，oauth2会写一个JSESSIONID到客户端，后续客户端请求只要cookie中
     * 有这个会话id即可，oauth2每次收到请求会首先到session中获取授权信息，获取不到则返回401
     * ,所以即使携带access_token访问一样会导致401 unauthorized，
     * 避免此问题需要启用EnableResourceServer注解，会增加OAuth2AuthenticationProcessingFilter过滤器
     * ，该过滤器处理通过access_token访问才会生效
     * <p>
     * EnableResourceServer注解会启用OAuth2的token认证，在原基础上增加OAuth2AuthenticationProcessingFilter过滤器
     * EnableResourceServer->(import ResourceServerConfiguration)->configure(http)->ResourceServerSecurityConfigurer
     * -> OAuth2AuthenticationProcessingFilter
     *
     * @author *已打码*
     */
    @Configuration
    @EnableResourceServer

    public static class UnityResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            //此处是关键，默认stateless=true，只支持access_token形式，
            // OAuth2客户端连接需要使用session，所以需要设置成false以支持session授权
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            //需要的时候创建session，支持从session中获取认证信息，ResourceServerConfiguration中
            //session创建策略是stateless不使用，这里其覆盖配置可创建session
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().antMatchers("/unity/**")
                    .and().authorizeRequests()
                    .antMatchers("/unity/**").access("#oauth.hasScopr('read') and hasRole('UNITY')");
        }
    }

    @Configuration
    @EnableResourceServer
    public static class MobilResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    // 所有以 /m/  开头的 URL属于此资源
                    .requestMatchers().antMatchers("/m/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/m/**");
//            .access("#oauth2.hasScope('read') and hasRole('MOBILE')");

        }
    }

    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;
        @Autowired
        private DefaultTokenServices tokenServices;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("123"))
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code")
                    .redirectUris("http://localhost:8082/index.html");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authorizationCodeServices(authorizationCodeServices()).tokenServices(tokenServices);
        }

        @Bean
        public AuthorizationCodeServices authorizationCodeServices() {
            return new InMemoryAuthorizationCodeServices();
        }
    }

}

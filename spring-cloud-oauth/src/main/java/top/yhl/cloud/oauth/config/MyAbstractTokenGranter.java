package top.yhl.cloud.oauth.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.yhl.cloud.oauth.entity.CustomAuthenticationToken;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyAbstractTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "YHL";
    private final AuthenticationManager authenticationManager;

    protected MyAbstractTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String openid = parameters.get("openid");
        Authentication userAuth = new CustomAuthenticationToken(openid);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package top.yhl.cloud.oauth.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.yhl.cloud.oauth.entity.CustomAuthenticationToken;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final String GRANT_TYPE = "YHL";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String grant_type = (String) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getAttribute("grant_type");
        if (!GRANT_TYPE.equals(grant_type)) {
            return null;
        }
        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) authentication;
        String openId = authenticationToken.getPrincipal().toString();
        String code = (String) authenticationToken.getCredentials();
        if(code == null){
            throw new BadCredentialsException("");
        }
        User user = userClient.getUserByOpenId(openid).requireSuccess();
        Assert.notNull(user, CommomCode.USER_NOT_FOUND.message());
        authService.vaildUserStatus(user.getStatus());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(userDetails.getAuthorities(), userDetails);
        return authenticationToken;
    }

    // 匹配自定义的认证授权类
    @Override
    public boolean supports(Class<?> aClass) {
        return CustomAuthenticationToken.class.isAssignableFrom(aClass);
    }
}

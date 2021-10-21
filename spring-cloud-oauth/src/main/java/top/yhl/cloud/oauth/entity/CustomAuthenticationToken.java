package top.yhl.cloud.oauth.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    // 账号
    private final Object principal;

    public CustomAuthenticationToken(Object username) {
        super(null);
        this.principal = username;
        setAuthenticated(false);
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if(authenticated){
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(authenticated);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

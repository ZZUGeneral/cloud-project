package top.yhl.cloud.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, MyWebAuthenticationDetails> {
    @Override
    public MyWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new MyWebAuthenticationDetails(request);
    }
}

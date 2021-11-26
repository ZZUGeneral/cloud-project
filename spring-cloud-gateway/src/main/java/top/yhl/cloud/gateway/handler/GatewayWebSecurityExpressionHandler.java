package top.yhl.cloud.gateway.handler;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import top.yhl.cloud.gateway.service.impl.PermissionServiceImpl;

import javax.annotation.Resource;

@Component
public class GatewayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Resource
    private PermissionServiceImpl permissionService;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(authentication, invocation);
        standardEvaluationContext.setVariable("permissionService", permissionService);
        return standardEvaluationContext;
    }
}

package top.yhl.cloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.yhl.cloud.security.entity.Menu;
import top.yhl.cloud.security.entity.Role;
import top.yhl.cloud.security.mapper.MenuMapper;

import java.util.Collection;
import java.util.List;

@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private MenuMapper menuMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取请求的 URL
        String url = ((FilterInvocation) o).getRequestUrl();
        //获取当前请求需要的角色信息，拿url去menu表中匹配，查看是访问的那个menu下的资源，根据menuId 获取到role信息
        List<Menu> menus = menuMapper.selectAllMenu();
        for (Menu menu : menus) {
            //如果请求的路径包含在某个menu的url中，且能访问该资源的角色信息存在
            if (antPathMatcher.match(menu.getUrl(), url) && menu.getRoles().size() > 0) {
                List<Role> roles = menu.getRoles();
                int size = roles.size();
                //定义一个数组，来接收能访问该资源的角色
                String[] roleNameArray = new String[size];
                for (int i = 0; i < size; i++) {
                    roleNameArray[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleNameArray);
            }
        }
        //如果遍历完menu之后没有匹配上，说名访问该资源不需要权限信息，设置一个登陆就能访问的角色
        return SecurityConfig.createList("ROLE_login");

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}

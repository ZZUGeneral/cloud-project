package top.yhl.cloud.gateway.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface PermissionService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
}

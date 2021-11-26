package top.yhl.cloud.gateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;
import top.yhl.cloud.gateway.service.PermissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.info("request url : " + request.getRequestURI());
        log.info("authentication : " + authentication.toString());
        return new Random().nextInt(20) % 20 == 0;
    }
}

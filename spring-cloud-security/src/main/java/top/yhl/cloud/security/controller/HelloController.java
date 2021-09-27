package top.yhl.cloud.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.yhl.cloud.security.config.MyWebAuthenticationDetails;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        System.out.println(details);
        return "hello";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }

    @GetMapping("/rememberme")
    public String rememberme() {
        return "rememberme";
    }
}

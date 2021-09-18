package top.yhl.cloud.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean isPassed;

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
//        String code = request.getParameter("code");
//        String verify_code = (String) request.getSession().getAttribute("verify_code");
//        if (code != null && verify_code != null && code.equals(verify_code)) {
//            isPassed = true;
//        }
        isPassed = true;
    }

    public boolean isPassed() {
        return isPassed;
    }
}
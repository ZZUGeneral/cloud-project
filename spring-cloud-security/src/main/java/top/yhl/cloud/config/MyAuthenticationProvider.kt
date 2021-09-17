package top.yhl.cloud.config

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails

class MyAuthenticationProvider : DaoAuthenticationProvider() {

    override fun additionalAuthenticationChecks(userDetails: UserDetails, authentication: UsernamePasswordAuthenticationToken) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String code = request.getParameter("code");
//        String verify_code = (String) request.getSession().getAttribute("verify_code");
//        if (code == null || verify_code == null || !code.equals(verify_code)) {
//            throw new AuthenticationServiceException("验证码错误");
//        }
        if (!(authentication.details as MyWebAuthenticationDetails).isPassed) {
            throw AuthenticationServiceException("验证码错误")
        }
        super.additionalAuthenticationChecks(userDetails, authentication)
    }
}
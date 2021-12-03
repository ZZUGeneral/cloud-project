package top.yhl.cloud.log.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;
import top.yhl.cloud.log.constants.BaseConstants;
import top.yhl.cloud.log.constants.StringPool;
import top.yhl.cloud.log.entity.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class HttpHeaderTool {
    /**
     * TOKEN前缀
     */
    public static final String FAWKES_AUTH = "Fawkes-Auth";
    public static final String HEADER_PREFIX = "Basic ";
    public static final String PORTAL_ID = "PortalId";
    public static final String FAWKES_ANALYSIS = "Fawkes-Analysis";

    public static String getHeaderValue(HeaderEnum headerEnum) {
        //可能存在request对象不存在 会造成切面日志出错的情况
        HttpServletRequest request = WebTool.getRequest();
        if (request == null) {
            return StringPool.EMPTY;
        }
        String value = request.getHeader(headerEnum.getKey());
        switch (headerEnum) {
            case IP_ADDRESS:
                value = StringUtils.isEmpty(value) ? BaseConstants.LOCAL_IP : value;
                break;
            case AUTH_USERNAME:
                CurrentUser currentUser = getCurrentUser();
                value = Objects.isNull(currentUser) ? BaseConstants.SYSTEM : currentUser.getUserName();
                break;
            case TX_ID:
                break;
            case CURRENT_USER:
                break;
            default:
                value = StringPool.EMPTY;
                break;
        }
        return value;
    }


    @Getter
    @AllArgsConstructor
    public enum HeaderEnum {
        AUTH_USERNAME("X-AUTH-USERNAME", "用户名称从全局用户获取"),
        IP_ADDRESS("X-IP-ADDRESS", "ip地址"),
        TX_ID("X-TX-ID", "全局id"),
        CURRENT_USER("CurrentUser", "全局用户");
        /**
         * 请求头的key
         */
        private String key;
        /**
         * 请求头的desc
         */
        private String desc;
    }


    /**
     * 获取当前用户
     *
     * @return
     */
    public static CurrentUser getCurrentUser() {
        HttpServletRequest request = WebTool.getRequest();
        if (request == null) {
            return buildCurrentUser(BaseConstants.SYSTEM, BaseConstants.SYSTEM_NAME, BaseConstants.DEFAULT_TENANT_ID);
        }
        String headerValue = request.getHeader(HeaderEnum.CURRENT_USER.getKey());
        if (StringUtils.isEmpty(headerValue)) {
            return buildCurrentUser(BaseConstants.SYSTEM, BaseConstants.SYSTEM_NAME, BaseConstants.DEFAULT_TENANT_ID);
        }
        CurrentUser currentUser = JsonTool.parse(headerValue, CurrentUser.class);
        try {
            currentUser.setUserFullname(StringUtils.isEmpty(currentUser.getUserFullname()) ? StringPool.EMPTY : URLDecoder.decode(currentUser.getUserFullname(), StringPool.UTF_8));
        } catch (UnsupportedEncodingException e) {

        }
        return currentUser;
    }


    /**
     * 构造当前用户
     *
     * @param userName
     * @param userFullname
     * @return
     */
    private static CurrentUser buildCurrentUser(String userName, String userFullname, Integer tenantId) {
        //请求头没有 再取一次请求参数中的
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUserFullname(userFullname);
        currentUser.setUserName(userName);
        currentUser.setTenantId(tenantId);
        return currentUser;
    }
}

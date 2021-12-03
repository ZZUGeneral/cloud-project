package top.yhl.cloud.log.constants;

public interface LogConstants {

    public static final String LOG_PREFIX = "LOG-" + Integer.MAX_VALUE + "-";

    /**
     * api日志
     */
    public static final String API_LOG = LOG_PREFIX + "ApiLog";

    /**
     * 异常日志
     */
    public static final String EXCEPTION_LOG = LOG_PREFIX + "ExceptionLog";


    /**
     * 常规日志
     */
    public static final String USUAL_LOG = LOG_PREFIX + "UsualLog";


    /**
     * 安全日志
     */
    public static final String SECURITY_LOG = "SecurityLog";


    /**
     * 安全日志:登录日志
     */
    public static final String LOGIN_LOG = "LoginLog";


    /**
     * 安全日志:登出日志
     */
    public static final String LOGINOUT_LOG = "LogoutLog";


    /**
     * 安全日志:角色越权访问
     */
    public static final String ROLE_UNAUTHORIZED_LOG = "RoleUnauthorizedLog";


    /**
     * 安全日志：非法token
     */
    public static final String TOKEN_UNAUTHORIZED_LOG = "TokenUnauthorizedLog";


    /**
     * 安全日志:非法签名
     */
    public static final String SIGN_UNAUTHORIZED_LOG = "SignUnauthorizedLog";


    /**
     * 安全日志:系统配置变更
     */
    public static final String SYS_CONFIG_CHANGE_LOG = "SysConfigChangeLog";


    /**
     * 安全级别：信息
     */
    public static final String SECURITY_LEVEL_INFO = "info";


    /**
     * 安全级别：警告
     */
    public static final String SECURITY_LEVEL_WARNING = "warning";


    /**
     * 安全级别：严重
     */
    public static final String SECURITY_LEVEL_SERIOUS = "serious";
}

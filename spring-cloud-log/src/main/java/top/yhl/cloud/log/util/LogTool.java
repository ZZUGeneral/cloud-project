package top.yhl.cloud.log.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.yhl.cloud.log.constants.BaseConstants;
import top.yhl.cloud.log.constants.LogConstants;
import top.yhl.cloud.log.entity.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class LogTool {
    private static final String OAUTH_TOKEN_SKIP = "/oauth/token";
    @Resource
    private EnvProperties envProperties;
    @Resource
    private ServerInfo serverInfo;


    public void debug(Logger logger, String log, String logType, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            UsualLog usualLog = new UsualLog();
            usualLog.setLogType(logType);
            usualLog.setLevel(LogConstants.SECURITY_LEVEL_INFO);
            logger.debug(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void info(Logger logger, String log, String logType, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            UsualLog usualLog = new UsualLog();
            usualLog.setLogType(logType);
            usualLog.setLevel(LogConstants.SECURITY_LEVEL_INFO);
            logger.info(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void warn(Logger logger, String log, String logType, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            UsualLog usualLog = new UsualLog();
            usualLog.setLogType(logType);
            usualLog.setLevel(LogConstants.SECURITY_LEVEL_WARNING);
            logger.warn(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void error(Logger logger, String log, String logType, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            UsualLog usualLog = new UsualLog();
            usualLog.setLogType(logType);
            usualLog.setLevel(LogConstants.SECURITY_LEVEL_SERIOUS);
            logger.error(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void debug(Logger logger, String log, UsualLog usualLog, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            usualLog = Objects.isNull(usualLog) ? new UsualLog() : usualLog;
            logger.debug(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void info(Logger logger, String log, UsualLog usualLog, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            usualLog = Objects.isNull(usualLog) ? new UsualLog() : usualLog;
            logger.info(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void warn(Logger logger, String log, UsualLog usualLog, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            usualLog = Objects.isNull(usualLog) ? new UsualLog() : usualLog;
            logger.warn(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    public void error(Logger logger, String log, UsualLog usualLog, Object... args) {
        if (logger != null && logger.isDebugEnabled() && StringUtils.hasLength(log)) {
            usualLog = Objects.isNull(usualLog) ? new UsualLog() : usualLog;
            logger.error(JsonTool.toJson(assembleUsualLog(log, usualLog)), args);
        }
    }

    private Object assembleUsualLog(String log, UsualLog usualLog) {
        String logType = usualLog.getLogType();
        logType = StringUtils.isEmpty(logType) ? LogConstants.USUAL_LOG : logType;
        LogTool.addRequestInfoToLog(WebTool.getRequest(), usualLog);
        LogTool.addOtherInfoToLog(usualLog, envProperties, serverInfo);
        usualLog.setLogData(log);

        HttpServletRequest request = WebTool.getRequest();
        String params = Objects.equals(OAUTH_TOKEN_SKIP, request.getRequestURI()) ? null : WebTool.getRequestContent(request);
        usualLog.setParams(params);
        usualLog.setLogType(LogConstants.USUAL_LOG + "-" + logType);
        Thread thread = Thread.currentThread();
        StackTraceElement[] traces = thread.getStackTrace();
        if (traces.length > 3) {
            usualLog.setMethodClass(traces[3].getClassName());
            usualLog.setMethodName(traces[3].getMethodName());
        }
        return usualLog;
    }

    public static void addRequestInfoToLog(HttpServletRequest request, Log log) {
        if (request != null) {
            CurrentUser currentUser = HttpHeaderTool.getCurrentUser();
            log.setTenantId(currentUser.getTenantId());
            log.setUserIp(ReactiveAddrTool.getIpAddress(request));
            log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
            log.setRequestURI(request.getRequestURI());
            log.setMethodType(request.getMethod());
            log.setUserName(currentUser.getUserName());
            log.setTxId(HttpHeaderTool.getHeaderValue(HttpHeaderTool.HeaderEnum.TX_ID));
        }
    }

    public static void addOtherInfoToLog(Log log, EnvProperties envProperties, ServerInfo serverInfo) {
        log.setServerName(serverInfo.getHostName());
        log.setServerIp(serverInfo.getIpWithPort());
        log.setServiceName(envProperties.getName());
        String env = envProperties.getEnv();
        log.setEnv(StringUtils.isEmpty(env) ? BaseConstants.DEV_CODE : env);
        log.setCreateTime(new Date());
    }

    public static String getStackTraceAsString(Throwable ex) {
        FastStringWriter writer = new FastStringWriter();
        ex.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public void exceptionLog(Throwable ex) {
        LogException logException = new LogException();
        logException.setLogType(LogConstants.EXCEPTION_LOG);
        LogTool.addRequestInfoToLog(WebTool.getRequest(), logException);
        LogTool.addOtherInfoToLog(logException, envProperties, serverInfo);
        logException.setStackTrace(LogTool.getStackTraceAsString(ex));
        logException.setExceptionName(ex.getClass().getName());
        logException.setMessage(ex.getMessage());
        logException.setParams(WebTool.getRequestContent(WebTool.getRequest()));
        StackTraceElement[] elements = ex.getStackTrace();
        if (elements != null && elements.length > 0) {
            StackTraceElement element = elements[0];
            logException.setMethodName(element.getMethodName());
            logException.setMethodClass(element.getClassName());
            logException.setFileName(element.getFileName());
            logException.setLineNumber(element.getLineNumber());
        }
        log.error(JsonTool.toJson(logException));
    }
}
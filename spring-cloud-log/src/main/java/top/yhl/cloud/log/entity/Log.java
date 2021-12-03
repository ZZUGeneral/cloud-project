package top.yhl.cloud.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("操作日志")
public class Log {
    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("全局调用链 txId")
    private String txId;

    @ApiModelProperty("服务名")
    private String serviceName;

    @ApiModelProperty("服务器IP")
    private String serverIp;

    @ApiModelProperty("服务器名")
    private String serverName;
    @ApiModelProperty("用户 IP")
    private String userIp;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户代理")
    private String userAgent;
    @ApiModelProperty("环境")
    private String env;

    @ApiModelProperty("租户 ID")
    private Integer tenantId;

    @ApiModelProperty("请求 URL")
    private String requestURI;

    @ApiModelProperty("操作方式")
    private String methodType;

    @ApiModelProperty("方法类")
    private String methodClass;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("操作参数")
    private String params;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}

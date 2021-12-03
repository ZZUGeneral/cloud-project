package top.yhl.cloud.log.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogException extends Log implements Serializable {

    @ApiModelProperty("堆栈信息")
    private String stackTrace;

    @ApiModelProperty("异常名称")
    private String exceptionName;

    @ApiModelProperty("异常信息")
    private String message;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("代码位置")
    private Integer lineNumber;
}

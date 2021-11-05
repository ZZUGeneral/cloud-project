package top.yhl.cloud.log.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsualLog extends Log {

    @ApiModelProperty("日志数据")
    private String logData;

    @ApiModelProperty("日志级别")
    private String level;
}

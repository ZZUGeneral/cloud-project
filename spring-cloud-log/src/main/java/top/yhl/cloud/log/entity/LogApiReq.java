package top.yhl.cloud.log.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yang_hl3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogApiReq extends Log implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日志标题")
    private String title;

    @ApiModelProperty("执行时间")
    private Long time;
}

package top.yhl.cloud.quartz.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 定时任务调度日志表 sys_job_log
 *
 * @author
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysJobLog extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "日志序号")
    private Long jobLogId;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @ExcelProperty(value = "任务组名")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @ExcelProperty(value = "调用目标字符串")
    private String invokeTarget;

    /**
     * 日志信息
     */
    @ExcelProperty(value = "日志信息")
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    @ExcelProperty(value = "执行状态")
    private String status;

    /**
     * 异常信息
     */
    @ExcelProperty(value = "异常信息")
    private String exceptionInfo;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 停止时间
     */
    private Date stopTime;
}

package top.yhl.cloud.log.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel("用户")
@Data
public class CurrentUser implements Serializable {
    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("姓名")
    private String userFullname;
    @ApiModelProperty("员工号")
    private String userNo;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("当前登录应用")
    private String clientId;
    @ApiModelProperty("租户ID")
    private Integer tenantId;
    @ApiModelProperty("头像 token")
    private String avatarToken;
    @ApiModelProperty("呢称")
    private String nickname;
    @ApiModelProperty("个人简介")
    private String introduction;
    @ApiModelProperty("生日")
    private Timestamp birthDay;
}

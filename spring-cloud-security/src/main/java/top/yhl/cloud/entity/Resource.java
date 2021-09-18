package top.yhl.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    /**
     * 编号
     */
    private Long id;
    /**
     * 资源名称
     */
    private String url;
    /**
     * 权限字符串
     */
    private List<Role> roles;


}

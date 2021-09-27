package top.yhl.cloud.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * 角色 ID
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态
     */
    private Integer status;
    /**
     * 角色拥有的资源
     */
    private List<Resource> resources;

}

package top.yhl.cloud.gateway.props;

import lombok.Data;

@Data
public class RouteResource {
    private static final String APPLICATION_VERSION = "1.0.0";

    // 文档名
    private String name;
    // 文档所在服务地址
    private String location;
    private String version = APPLICATION_VERSION;
}

package top.yhl.cloud.gateway.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import top.yhl.cloud.gateway.props.RouteProperties;
import top.yhl.cloud.gateway.props.RouteResource;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合各个服务的 swagger 接口
 */
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URL = "/v2/api-docs-ext";

    private RouteProperties routeProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<RouteResource> routeResources = routeProperties.getResources();
        routeResources.forEach(routeResource -> resources.add(swaggerResource(routeResource)));
        return resources;
    }

    private SwaggerResource swaggerResource(RouteResource routeResource) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(routeResource.getName());
        swaggerResource.setLocation(routeResource.getLocation());
        swaggerResource.setSwaggerVersion(routeResource.getVersion());
        return swaggerResource;
    }
}

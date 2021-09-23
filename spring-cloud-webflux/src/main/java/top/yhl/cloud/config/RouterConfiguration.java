package top.yhl.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.yhl.cloud.handlelr.PersonHandler;
import top.yhl.cloud.handlelr.UserHandler;

@Configuration
public class RouterConfiguration {
    @Bean
    RouterFunction<ServerResponse> personRouter(PersonHandler personHandler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/person"), RouterFunctions.route(
                        RequestPredicates.POST("/"), personHandler::addPerson)
                        .andRoute(RequestPredicates.GET("/"), personHandler::getAllPerson)
                        .andRoute(RequestPredicates.DELETE("/{id}"), personHandler::deletePerson));
    }

    @Bean
    RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/user"),
                RouterFunctions.route(RequestPredicates.GET("/"), userHandler::getAllUsers)
                        .andRoute(RequestPredicates.POST("/"), userHandler::addUser)
                        .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUser));
    }
}

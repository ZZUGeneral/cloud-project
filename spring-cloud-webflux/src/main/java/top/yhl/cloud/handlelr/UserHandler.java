package top.yhl.cloud.handlelr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.yhl.cloud.dao.UserRepository;
import top.yhl.cloud.entity.User;

import static java.lang.Long.parseLong;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> addUser(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(userRepository.saveAll(serverRequest.bodyToMono(User.class)), User.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        return userRepository.findById(parseLong(serverRequest.pathVariable("id")))
                .flatMap(user -> userRepository.delete(user).then(ok().build()))
                .switchIfEmpty(notFound().build());
    }
}

package top.yhl.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.yhl.cloud.dao.UserRepository;
import top.yhl.cloud.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/addUser")
    public Mono<User> addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/getAll")
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public Mono deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public Mono updateUser(@RequestBody User user) {
        return userRepository.findById(user.getId())
                .flatMap(u -> userRepository.save(user))
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}

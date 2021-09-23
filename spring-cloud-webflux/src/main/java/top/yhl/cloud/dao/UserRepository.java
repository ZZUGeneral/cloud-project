package top.yhl.cloud.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import top.yhl.cloud.entity.User;

@EnableMongoRepositories
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}

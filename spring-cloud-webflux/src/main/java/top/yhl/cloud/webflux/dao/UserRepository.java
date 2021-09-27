package top.yhl.cloud.webflux.dao;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import top.yhl.cloud.webflux.entity.User;

@EnableMongoRepositories
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}

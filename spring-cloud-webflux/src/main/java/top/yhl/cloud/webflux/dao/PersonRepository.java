package top.yhl.cloud.webflux.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import top.yhl.cloud.webflux.entity.Person;

@EnableMongoRepositories
public interface PersonRepository  extends ReactiveMongoRepository<Person,Long> {
}

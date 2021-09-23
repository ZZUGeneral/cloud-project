package top.yhl.cloud.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import top.yhl.cloud.entity.Person;

@EnableMongoRepositories
public interface PersonRepository  extends ReactiveMongoRepository<Person,Long> {
}

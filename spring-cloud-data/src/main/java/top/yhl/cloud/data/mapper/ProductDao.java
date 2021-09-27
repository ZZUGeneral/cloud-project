package top.yhl.cloud.data.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.yhl.cloud.data.entity.Product;

@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}

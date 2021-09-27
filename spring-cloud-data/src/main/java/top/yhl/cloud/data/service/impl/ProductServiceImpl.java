package top.yhl.cloud.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yhl.cloud.data.mapper.ProductDao;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductDao productDao;

}

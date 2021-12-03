package com.yhl.cloud.generator;

import com.yhl.cloud.generator.mapper.HdBookmarkMapper;
import com.yhl.cloud.generator.model.HdBookmark;
import com.yhl.cloud.generator.model.HdBookmarkExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringCloudGeneratorApplicationTests {
    @Autowired
    HdBookmarkMapper bookmarkMapper;

    @Test
    void contextLoads() {
        HdBookmarkExample example = new HdBookmarkExample();
        example.setOrderByClause("create_date");
        example.setDistinct(true);
        List<HdBookmark> list = bookmarkMapper.selectByExample(example);
        System.out.println(list);
    }

}

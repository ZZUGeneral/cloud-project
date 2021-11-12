package com.yhl.cloud.generator.common;

import com.yhl.cloud.generator.common.mapper.HdBookmarkMapper;
import com.yhl.cloud.generator.common.model.HdBookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@ResponseBody
public class TestController {
    @Autowired
    HdBookmarkMapper mapper;

    @GetMapping("/bookmark")
    public List<HdBookmark> getBookmark() {
        return mapper.selectByExample(null);
    }
}

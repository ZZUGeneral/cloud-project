package com.yhl.cloud.generator.common.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yang_hl3
 */
@Component
public class BookmarkDictTypeHandler extends AbstractJsonTypeHandler<List<BookmarkDict>> {
    @Override
    protected List<BookmarkDict> parse(String json) {
        return JSON.parseArray(json, BookmarkDict.class);
    }

    @Override
    protected String toJson(List<BookmarkDict> obj) {
        return JSON.toJSONString(obj);
    }
}

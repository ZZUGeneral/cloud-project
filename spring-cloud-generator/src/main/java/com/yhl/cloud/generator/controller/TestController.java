package com.yhl.cloud.generator.controller;

import com.alibaba.excel.EasyExcel;
import com.yhl.cloud.generator.mapper.HdBookmarkMapper;
import com.yhl.cloud.generator.model.HdBookmark;
import com.yhl.cloud.generator.model.HdBookmarkExample;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    HdBookmarkMapper bookmarkMapper;

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        HdBookmarkExample example = new HdBookmarkExample();
        example.setOrderByClause("create_date");
        example.setDistinct(true);
        List<HdBookmark> list = bookmarkMapper.selectByExample(example);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String filename = URLEncoder.encode("导出", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
        EasyExcel.write(response.getOutputStream(), HdBookmark.class).sheet("aaaaaaaaaa").doWrite(list);
    }
}

package com.yhl.cloud.generator.common.model;


import java.io.Serializable;
import java.util.List;

public class BookmarkDict implements Serializable {
    public String name;
    public List<BookmarkDict> children;
    public List<Mark> web;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookmarkDict> getChildren() {
        return children;
    }

    public void setChildren(List<BookmarkDict> children) {
        this.children = children;
    }

    public List<Mark> getWeb() {
        return web;
    }

    public void setWeb(List<Mark> web) {
        this.web = web;
    }
}

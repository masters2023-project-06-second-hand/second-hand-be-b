package com.codesquad.secondhand.adapter.in.web.response;

public class CategorySimpleDetail {

    private Long id;
    private String name;

    public CategorySimpleDetail(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

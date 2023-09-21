package com.codesquad.secondhand.adapter.in.web.query.prodcut.response;

public class CategorySimpleDetail {

    private final Long id;
    private final String name;

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

package com.codesquad.secondhand.adapter.in.web.response;

import lombok.Getter;

@Getter
public class RegionInfo {

    private Long id;
    private String name;

    public RegionInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

package com.codesquad.secondhand.adapter.in.web.common.response;

import lombok.Getter;

@Getter
public class RegionInfo {

    private final Long id;
    private final String name;

    public RegionInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

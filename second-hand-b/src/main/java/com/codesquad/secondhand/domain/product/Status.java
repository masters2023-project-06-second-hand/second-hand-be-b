package com.codesquad.secondhand.domain.product;

import java.util.Arrays;

public enum Status {
    ONSALES("판매중"),
    RESERVED("예약중"),
    SOLDOUT("판매완료");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Status findByName(String name) {
        return Arrays.stream(values())
                .filter(status -> status.name.equals(name))
                .findFirst()
                .orElse(null);
    }
}

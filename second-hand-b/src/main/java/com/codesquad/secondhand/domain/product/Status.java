package com.codesquad.secondhand.domain.product;

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
}

package com.codesquad.secondhand.domain.product;

public enum Category {
    DIGITAL("디지털기기"),
    INTERIOR("가구/인테리어");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}

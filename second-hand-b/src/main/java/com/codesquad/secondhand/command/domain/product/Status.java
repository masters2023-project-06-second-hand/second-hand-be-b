package com.codesquad.secondhand.command.domain.product;

import com.codesquad.secondhand.common.exception.BadRequestException;
import java.util.Arrays;

public enum Status {
    ON_SALES("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("판매완료");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public static boolean isSoldOut(Status status) {
        return status.equals(SOLD_OUT);
    }

    public String getName() {
        return name;
    }

    public static Status findByName(String name) {
        return Arrays.stream(values())
                .filter(status -> status.name.equals(name))
                .findFirst()
                .orElseThrow(BadRequestException::new);
    }
}

package com.codesquad.secondhand.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Common
    PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "C001", "이 작업을 수행하기 위한 필요한 권한이 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C002", "잘못 된 요청입니다."),
    INVALID_ENTITY_STATE(HttpStatus.BAD_REQUEST, "C003", "엔터티는 저장 후 유효한 ID를 가져야 하는데 null을 발견했습니다"),

    // Auth
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A001", "토큰이 유효하지 않습니다. 다시 로그인해주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 리프레시 토큰입니다. 새로운 토큰을 요청해주세요."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "M001", "사용자를 찾을 수 없습니다."),
    NOT_REGISTERED_MEMBER(HttpStatus.UNAUTHORIZED, "M002", "사용자는 등록된 멤버가 아닙니다. 가입을 먼저 진행해주세요."),

    // Region
    REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "R001", "동네를 찾을 수 없습니다."),
    EXISTS_MEMBER_REGION(HttpStatus.BAD_REQUEST, "R002", "이미 존재하는 멤버의 동네입니다."),
    NOT_EXISTS_MEMBER_REGION(HttpStatus.BAD_REQUEST, "R003", "사용자의 동네와 일치하지 않습니다."),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "P001", "상품을 찾을 수 없습니다."),
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "P002", "이미지를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "P003", "카테고리를 찾을 수 없습니다."),

    // Image
    MINIMUM_IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "I001", "이미지는 최소 하나는 있어야 한다."),

    // Chat
    CHAT_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "CH01", "채팅방을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

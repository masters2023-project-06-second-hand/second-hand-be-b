package com.codesquad.secondhand.command.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ANONYMOUS("ROLE_ANONYMOUS", "비로그인 사용자"),
    USER("ROLE_USER", "일반 사용자"),
    MEMBER("ROLE_MEMBER", "가입된 사용자"),
    MANAGER("ROLE_MANAGER", "관리자");
    private final String key;
    private final String title;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}

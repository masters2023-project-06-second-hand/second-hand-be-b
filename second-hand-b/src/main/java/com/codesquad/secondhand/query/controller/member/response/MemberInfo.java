package com.codesquad.secondhand.query.controller.member.response;

import lombok.Getter;

@Getter
public class MemberInfo {

    private final Long id;
    private final String nickname;
    private final String profileImg;

    public MemberInfo(Long id, String nickname, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}

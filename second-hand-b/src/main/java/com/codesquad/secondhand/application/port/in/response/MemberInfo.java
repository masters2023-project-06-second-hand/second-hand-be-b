package com.codesquad.secondhand.application.port.in.response;

import lombok.Getter;

@Getter
public class MemberInfo {

    private Long id;
    private String nickname;
    private String profileImg;

    public MemberInfo(Long id, String nickname, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}

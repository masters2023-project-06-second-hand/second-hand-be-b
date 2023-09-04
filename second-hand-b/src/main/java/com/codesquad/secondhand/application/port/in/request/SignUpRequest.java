package com.codesquad.secondhand.application.port.in.request;

import java.util.List;

public class SignUpRequest {

    private String nickname;
    private String profileImg;
    private List<Long> regionsId;

    public SignUpRequest() {
    }

    public SignUpRequest(String nickname, String profileImg, List<Long> regionsId) {
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.regionsId = regionsId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public List<Long> getRegionsId() {
        return regionsId;
    }
}

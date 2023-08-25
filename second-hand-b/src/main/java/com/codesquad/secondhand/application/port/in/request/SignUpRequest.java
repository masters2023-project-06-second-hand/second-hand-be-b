package com.codesquad.secondhand.application.port.in.request;

import java.util.List;

public class SignUpRequest {

    private String nickname;
    private String profileImg;
    private List<Long> regionsId;

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

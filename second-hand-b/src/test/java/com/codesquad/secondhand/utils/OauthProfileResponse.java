package com.codesquad.secondhand.utils;


public class OauthProfileResponse {

    private String email;
    private Integer age;

    public OauthProfileResponse() {
    }

    public OauthProfileResponse(String email, Integer age) {
        this.email = email;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}

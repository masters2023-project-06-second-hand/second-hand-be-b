package com.codesquad.secondhand.adapter.in.web;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.oauth.WithTestUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ObjectMapper objectMapper;

    @WithTestUser
    @DisplayName("Oauth2.0 인증 했지만 해당 유저가 없으면 signup Token을 발급하여 가입하라고 redirect 한다")
    @Test
    void signIn() throws Exception {
        mockMvc.perform(get("/api/members/signin"))
                .andDo(print())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, startsWith("Bearer ")))
                .andExpect(status().is3xxRedirection())
        ;
    }

    @Test
    void signup() throws Exception {
        // given
        String signUpToken = jwtTokenProvider.createSignUpToken("test@email.com");
        SignUpRequest signUpRequest = new SignUpRequest("이안", "url", List.of(1L));

        // then
        mockMvc.perform(post("/api/members/signup")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + signUpToken)
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("AccessToken").exists())
                .andExpect(status().isOk());
    }
}

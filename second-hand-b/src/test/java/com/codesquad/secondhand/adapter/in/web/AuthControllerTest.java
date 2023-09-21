package com.codesquad.secondhand.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.secondhand.adapter.in.web.common.security.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.common.security.response.Tokens;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.service.in.common.AuthService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    public static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_NICKNAME = "이안";
    public static final String TEST_PROFILE_IMAGE = "url";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    AuthService authService;

    private String getTestRefreshToken(Member member, Date startDate) {
        return JwtTokenProvider.createRefreshToken(TEST_EMAIL, member.getIdStringValue(), startDate);
    }

    private Member getTestMember() {
        return memberRepository.save(
                new Member(TEST_EMAIL, TEST_NICKNAME, TEST_PROFILE_IMAGE, Role.USER));
    }

    @DisplayName("signUpToken와 함께 회원가입을 요청하면 Tokens를 보낸다")
    @Test
    void shouldReturnTokensWhenSignUpWithSignUpToken() throws Exception {
        // given
        String signUpToken = JwtTokenProvider.createSignUpToken(TEST_EMAIL);
        SignUpRequest signUpRequest = new SignUpRequest(TEST_NICKNAME, TEST_PROFILE_IMAGE, List.of(1L));

        // when,then
        mockMvc.perform(post("/api/members/signup")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + signUpToken)
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("refreshToken").exists())
                .andExpect(jsonPath("memberId").exists())
                .andExpect(status().isOk());
    }

    @DisplayName("refreshToken으로 AccessToken을 요청하면 새로 만든 Tokens를 보낸다")
    @Test
    void shouldReturnNewTokensWhenRequestedWithRefreshToken() throws Exception {
        // given
        Member member = getTestMember();
        Tokens tokens = authService.signIn(member.getEmail());
        Map<String, String> body = Map.of("refreshToken", tokens.getRefreshToken());

        // when,then
        mockMvc.perform(post("/api/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andDo(print())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("refreshToken").exists())
                .andExpect(status().isOk());
    }

    @DisplayName("무효한 refreshToken으로 AccessToken을 요청하면 새로 만든 Tokens를 보낸다")
    @Test
    void shouldReturnErrorMessageWhenRequestedWithInvalidRefreshToken() throws Exception {
        // given
        Member member = getTestMember();
        Date startDate = new Date();
        String refreshToken = getTestRefreshToken(member, startDate);
        Map<String, String> content = Map.of("refreshToken", refreshToken);

        // when,then
        mockMvc.perform(post("/api/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
        ;
    }

    @DisplayName("만료된 refreshToken으로 AccessToken을 요청하면 에러 메시지를 보낸다")
    @Test
    void shouldReturnErrorMessageWhenRequestedAccessTokenWithExpiredRefreshToken() throws Exception {
        // given
        Member member = getTestMember();
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String refreshToken = getTestRefreshToken(member, startDate);
        Map<String, String> content = Map.of("refreshToken", refreshToken);

        // when,then
        mockMvc.perform(post("/api/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
        ;
    }

    @DisplayName("로그인 아웃 요청 후 refresh 토큰을 다시 사용할 수 없다")
    @Test
    void shouldInvalidateRefreshTokenAfterLogout() throws Exception {
        // given
        Member member = getTestMember();
        Tokens tokens = authService.signIn(member.getEmail());
        Map<String, String> body = Map.of("refreshToken", tokens.getRefreshToken());

        // when
        mockMvc.perform(post("/api/members/signout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        //
        mockMvc.perform(post("/api/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
        ;
    }
}

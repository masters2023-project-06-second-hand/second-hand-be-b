package com.codesquad.secondhand.adapter.in.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.oauth.WithTestUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.hamcrest.Matcher;
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

    private static Matcher<String> MatcherBearer() {
        return startsWith("Bearer ");
    }

    @WithTestUser
    @DisplayName("OAuth2.0 인증 했지만 해당 유저가 없으면 signup Token을 발급하여 가입하라고 redirect 한다")
    @Test
    void givenOAuth2Authentication_whenUserDoesNotExist_thenIssueSignupTokenAndRedirectForRegistration()
            throws Exception {
        mockMvc.perform(get("/api/members/signin"))
                .andDo(print())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, MatcherBearer()))
                .andExpect(status().is3xxRedirection())
        ;
    }

    private String getTestRefreshToken(Member member, Date startDate) {
        return JwtTokenProvider.createRefreshToken(TEST_EMAIL, member.getIdStringValue(), startDate);
    }

    private Member getTestMember() {
        return memberRepository.save(
                new Member(TEST_EMAIL, TEST_NICKNAME, TEST_PROFILE_IMAGE, null, Role.USER));
    }

    @WithTestUser
    @DisplayName("Oauth2.0 인증 했고 행당 유저가 있으면 AccessToken를 Authtication Header로 RefreshToken은 Cookie Header에 담은 다음 Redirect한다")
    @Test
    void givenOAuth2Authentication_whenUserExists_thenSetTokensInHeadersAndRedirect() throws Exception {
        getTestMember();

        mockMvc.perform(get("/api/members/signin"))
                .andDo(print())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, MatcherBearer()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("refresh_token")))
        ;
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
                .andExpect(status().isOk());
    }

    @DisplayName("refreshToken으로 AccessToken을 요청하면 새로 만든 Tokens를 보낸다")
    @Test
    void shouldReturnNewTokensWhenRequestedWithRefreshToken() throws Exception {
        // given
        Member member = getTestMember();
        Date startDate = new Date();
        String refreshToken = getTestRefreshToken(member, startDate);

        // when,then
        mockMvc.perform(get("/api/members/accesstoken")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                )
                .andDo(print())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("refreshToken").exists())
                .andExpect(status().isOk());
    }

    @DisplayName("만료된 refreshToken으로 AccessToken을 요청하면 에러 메시지를 보낸다")
    @Test
    void shouldReturnErrorMessageWhenRequestedAccessTokenWithExpiredRefreshToken() throws Exception {
        // given
        Member member = getTestMember();
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String refreshToken = getTestRefreshToken(member, startDate);

        // when,then
        mockMvc.perform(get("/api/members/accesstoken")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
        ;
    }
}

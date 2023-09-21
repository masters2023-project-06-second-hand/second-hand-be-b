package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.application.service.in.common.AuthService;
import com.codesquad.secondhand.application.service.in.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.service.in.common.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.service.in.query.MemberQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    MemberQueryService memberQueryService;

    @DisplayName("이메일로 로그인 요청시,이메일 존재하지 않은 예외가 발생하면 예외를 잡고 가입하지 않는 예외를 던진다")
    @Test
    void signIn() {
        // given
        String notExistsEmail = "notExists@email.com";
        given(memberQueryService.getByEmail(notExistsEmail)).willThrow(MemberNotFoundException.class);

        // when,then
        assertThatThrownBy(() -> authService.signIn(notExistsEmail)).isInstanceOf(NotRegisteredMemberException.class);
    }
}

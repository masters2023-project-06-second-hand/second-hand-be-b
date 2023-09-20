package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.codesquad.secondhand.application.service.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.service.in.exception.PermissionDeniedException;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;
    @Mock
    MemberRepository memberRepository;

    @DisplayName("이메일로 멤버를 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void findByEmail() {
        // given
        String notExistsEmail = "notExists@email.com";
        given(memberRepository.findByEmail(notExistsEmail)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> memberService.getByEmail(notExistsEmail))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("아이디로 멤버를 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void getById() {
        // given
        long notExistsId = 1;
        given(memberRepository.findById(notExistsId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> memberService.getById(notExistsId))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("멤버와 요청시 멤버의 아이디가 다르면 예외를 던진다")
    @Test
    void validateMemberPermission() {
        // given
        long notSameId = 1;
        Member member = mock(Member.class);
        given(member.isSameId(1)).willReturn(false);

        // when
        assertThatThrownBy(() -> MemberUtils.validateMemberPermission(member, notSameId))
                .isInstanceOf(PermissionDeniedException.class);
    }
}

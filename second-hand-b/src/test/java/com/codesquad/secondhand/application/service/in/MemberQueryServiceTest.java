package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.command.port.out.MemberRepository;
import com.codesquad.secondhand.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.common.exception.PermissionDeniedException;
import com.codesquad.secondhand.common.utils.MemberUtils;
import com.codesquad.secondhand.query.service.MemberQueryService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @InjectMocks
    MemberQueryService memberQueryService;
    @Mock
    MemberRepository memberRepository;

    @DisplayName("이메일로 멤버를 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void findByEmail() {
        // given
        String notExistsEmail = "notExists@email.com";
        given(memberRepository.findByEmail(notExistsEmail)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> memberQueryService.getByEmail(notExistsEmail))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("아이디로 멤버를 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void getById() {
        // given
        long notExistsId = 1;
        given(memberRepository.findById(notExistsId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> memberQueryService.getById(notExistsId))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("멤버와 요청시 멤버의 아이디가 다르면 예외를 던진다")
    @Test
    void validateMemberPermission() {
        // given
        long notSameId = 1;

        // when
        assertThatThrownBy(() -> MemberUtils.validateMemberPermission("2", notSameId))
                .isInstanceOf(PermissionDeniedException.class);
    }
}

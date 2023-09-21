package com.codesquad.secondhand.application.service.in.query;

import static com.codesquad.secondhand.application.service.in.common.utils.MemberUtils.validateMemberPermission;

import com.codesquad.secondhand.adapter.in.web.query.member.response.MemberInfo;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.service.in.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public MemberInfo getProfile(String validatedMemberId, Long memberId) {

        validateMemberPermission(validatedMemberId, memberId);
        Member member = getById(memberId);
        return new MemberInfo(member.getId(), member.getNickname(), member.getProfileImage());
    }


    public Member getById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member signUpMember(String email, String nickname, String profileImg) {
        Member member = new Member(email, nickname, profileImg, Role.MEMBER);
        return save(member);
    }
}

package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public String signIn(String email) {
        Member member = getByEmail(email);
        return jwtTokenProvider.createAccessToken(member.getEmail(), String.valueOf(member.getId()));
    }

    @Transactional
    @Override
    public String signUp(String email, SignUpRequest signUpRequest) {
        Member member = toMember(email, signUpRequest);
        Member savedMember = memberRepository.save(member);
        return jwtTokenProvider.createAccessToken(savedMember.getEmail(), String.valueOf(savedMember.getId()));
    }

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> {
                    throw new MemberNotFoundException();
                });
    }

    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotRegisteredMemberException(jwtTokenProvider.createSignUpToken(email));
                });
    }

    private static Member toMember(String email, SignUpRequest signUpRequest) {
        return new Member(
                email,
                signUpRequest.getNickname(),
                signUpRequest.getProfileImg(),
                null,
                Role.MEMBER
        );
    }
}

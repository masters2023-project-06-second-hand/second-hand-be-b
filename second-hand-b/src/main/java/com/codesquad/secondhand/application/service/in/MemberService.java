package com.codesquad.secondhand.application.service.in;

import static com.codesquad.secondhand.application.service.in.MemberUtils.validateMemberPermission;

import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.service.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public MemberInfo getProfile(Member member, Long memberId) {
        validateMemberPermission(member, memberId);
        return new MemberInfo(member.getId(), member.getNickname(), member.getProfileImage());
    }

    @Transactional
    public void toggleProductLikeStatus(Member member, boolean isLiked, Product product) {
        Member savedMember = getById(member.getId());
        if (isLiked) {
            savedMember.addLikes(product);
            return;
        }
        savedMember.removeLikes(product);
    }


    public Member getById(Long memberId) {
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

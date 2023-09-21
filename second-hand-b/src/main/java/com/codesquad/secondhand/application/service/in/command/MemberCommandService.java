package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.service.in.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public void toggleProductLikeStatus(String memberIdValue, boolean isLiked, long productId) {
        long memberId = Long.parseLong(memberIdValue);
        Member savedMember = getById(memberId);
        if (isLiked) {
            savedMember.addLikes(productId);
            return;
        }
        savedMember.removeLikes(productId);
    }


    public Member getById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}

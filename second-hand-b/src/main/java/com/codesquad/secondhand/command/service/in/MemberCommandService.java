package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.port.out.MemberRepository;
import com.codesquad.secondhand.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.command.domain.member.Member;
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

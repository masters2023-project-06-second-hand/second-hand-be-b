package com.codesquad.secondhand.adapter.in.web.controller;

import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberUseCase memberUseCase;

    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberInfo> getProfile(
            @AuthenticationPrincipal Member member,
            @PathVariable Long memberId) {
        MemberInfo memberInfo = memberUseCase.getProfile(member, memberId);
        return ResponseEntity.ok().body(memberInfo);
    }

    @GetMapping("/api/members/{memberId}/likes/categories")
    public ResponseEntity<List<CategorySimpleDetail>> fetchMemberInterestCategories(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId
    ) {
        List<CategorySimpleDetail> categories = memberUseCase.fetchMemberInterestCategories(member, memberId);
        return ResponseEntity.ok(categories);
    }
}

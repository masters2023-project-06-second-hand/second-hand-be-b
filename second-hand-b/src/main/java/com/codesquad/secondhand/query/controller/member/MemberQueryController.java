package com.codesquad.secondhand.query.controller.member;

import com.codesquad.secondhand.query.controller.prodcut.response.CategorySimpleDetail;
import com.codesquad.secondhand.query.controller.member.response.MemberInfo;
import com.codesquad.secondhand.query.port.MemberQueryUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MemberQueryController {

    private final MemberQueryUseCase memberQueryUseCase;

    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberInfo> getProfile(
            @AuthenticationPrincipal String validatedMemberId,
            @PathVariable Long memberId) {
        MemberInfo memberInfo = memberQueryUseCase.getProfile(validatedMemberId, memberId);
        return ResponseEntity.ok().body(memberInfo);
    }

    @GetMapping("/api/members/{memberId}/likes/categories")
    public ResponseEntity<List<CategorySimpleDetail>> fetchMemberInterestCategories(
            @AuthenticationPrincipal String validatedMemberId,
            @PathVariable long memberId
    ) {
        List<CategorySimpleDetail> categories = memberQueryUseCase.fetchMemberInterestCategories(validatedMemberId, memberId);
        return ResponseEntity.ok(categories);
    }
}

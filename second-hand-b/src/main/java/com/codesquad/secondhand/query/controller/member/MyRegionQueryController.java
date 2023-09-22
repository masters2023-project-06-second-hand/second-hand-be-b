package com.codesquad.secondhand.query.controller.member;

import com.codesquad.secondhand.query.controller.member.response.MemberRegionInfos;
import com.codesquad.secondhand.query.port.MyRegionsQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/regions")
@RestController
public class MyRegionQueryController {

    private final MyRegionsQueryUseCase myRegionsQueryUseCase;

    @GetMapping
    public ResponseEntity<MemberRegionInfos> getRegionsOfMember(@PathVariable Long memberId) {
        MemberRegionInfos memberRegionList = myRegionsQueryUseCase.getRegionsOfMember(memberId);
        return ResponseEntity.ok()
                .body(memberRegionList);
    }
}


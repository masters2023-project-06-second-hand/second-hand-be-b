package com.codesquad.secondhand.adapter.in.web.query.member;

import com.codesquad.secondhand.adapter.in.web.query.member.response.MemberRegionInfos;
import com.codesquad.secondhand.application.port.in.query.MyRegionsQueryUseCase;
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


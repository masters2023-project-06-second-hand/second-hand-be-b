package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.MemberRegionUseCase;
import com.codesquad.secondhand.application.port.in.response.MemberRegionList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/regions")
@RestController
public class MemberRegionController {

    private final MemberRegionUseCase memberRegionUseCase;

    @PostMapping
    public ResponseEntity<Void> addRegionToMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        memberRegionUseCase.addRegionToMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeRegionFromMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        memberRegionUseCase.removeRegionFromMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<MemberRegionList> getRegionsOfMember(@PathVariable Long memberId) {
        MemberRegionList memberRegionList = memberRegionUseCase.getRegionsOfMember(memberId);
        return ResponseEntity.ok()
                .body(memberRegionList);
    }
}


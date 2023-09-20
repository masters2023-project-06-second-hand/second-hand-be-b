package com.codesquad.secondhand.adapter.in.web.controller;

import com.codesquad.secondhand.application.port.in.MyRegionsUseCase;
import com.codesquad.secondhand.adapter.in.web.response.MemberRegionInfos;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/regions")
@RestController
public class MemberRegionController {

    private final MyRegionsUseCase myRegionsUseCase;

    @PostMapping
    public ResponseEntity<Void> addRegionToMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsUseCase.addRegionToMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeRegionFromMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsUseCase.removeRegionFromMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<MemberRegionInfos> getRegionsOfMember(@PathVariable Long memberId) {
        MemberRegionInfos memberRegionList = myRegionsUseCase.getRegionsOfMember(memberId);
        return ResponseEntity.ok()
                .body(memberRegionList);
    }

    @PutMapping
    public ResponseEntity<Void> selectRegionForMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsUseCase.selectRegionForMember(memberId, request.get("id"));
        return ResponseEntity.ok().build();
    }
}


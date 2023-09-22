package com.codesquad.secondhand.command.adapter.in.web.member;

import com.codesquad.secondhand.command.port.in.MyRegionsCommandUseCase;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/regions")
@RestController
public class MyRegionCommandController {

    private final MyRegionsCommandUseCase myRegionsCommandUseCase;

    @PostMapping
    public ResponseEntity<Void> addRegionToMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsCommandUseCase.addRegionToMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeRegionFromMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsCommandUseCase.removeRegionFromMember(memberId, request.get("id"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<Void> selectRegionForMember(@PathVariable Long memberId,
            @RequestBody Map<String, Long> request) {
        myRegionsCommandUseCase.selectRegionForMember(memberId, request.get("id"));
        return ResponseEntity.ok().build();
    }
}


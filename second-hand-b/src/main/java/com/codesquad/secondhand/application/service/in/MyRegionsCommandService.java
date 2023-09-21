package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.command.MyRegionsCommandUseCase;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyRegionsCommandService implements MyRegionsCommandUseCase {

    private final MemberService memberService;
    private final RegionService regionService;

    @Transactional
    @Override
    public void addRegionToMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        regionService.validateRegionId(regionId);
        member.addRegion(regionId);
    }

    @Transactional
    @Override
    public void removeRegionFromMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        regionService.validateRegionId(regionId);
        member.removeRegion(regionId);
    }

    @Transactional
    @Override
    public void selectRegionForMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        regionService.validateRegionId(regionId);
        member.selectRegion(regionId);
    }
}

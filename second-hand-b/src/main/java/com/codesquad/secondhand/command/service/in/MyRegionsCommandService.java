package com.codesquad.secondhand.command.service.in;


import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.port.in.MyRegionsCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyRegionsCommandService implements MyRegionsCommandUseCase {

    private final MemberCommandService memberCommandService;
    private final RegionCommandService regionQueryService;

    @Transactional
    @Override
    public void addRegionToMember(Long memberId, Long regionId) {
        Member member = memberCommandService.getById(memberId);
        regionQueryService.validateRegionId(regionId);
        member.addRegion(regionId);
    }

    @Transactional
    @Override
    public void removeRegionFromMember(Long memberId, Long regionId) {
        Member member = memberCommandService.getById(memberId);
        regionQueryService.validateRegionId(regionId);
        member.removeRegion(regionId);
    }

    @Transactional
    @Override
    public void selectRegionForMember(Long memberId, Long regionId) {
        Member member = memberCommandService.getById(memberId);
        regionQueryService.validateRegionId(regionId);
        member.selectRegion(regionId);
    }
}

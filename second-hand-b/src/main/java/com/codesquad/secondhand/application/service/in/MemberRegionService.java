package com.codesquad.secondhand.application.service.in;


import static com.codesquad.secondhand.application.service.in.region.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.adapter.in.web.response.MemberRegionInfos;
import com.codesquad.secondhand.adapter.in.web.response.RegionInfo;
import com.codesquad.secondhand.application.port.in.MemberRegionUseCase;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberRegionService implements MemberRegionUseCase {

    private final MemberService memberService;
    private final RegionService regionService;

    @Transactional
    @Override
    public void addRegionToMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        Region region = regionService.getById(regionId);
        member.addRegion(region);
    }

    @Transactional
    @Override
    public void removeRegionFromMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        Region region = regionService.getById(regionId);
        member.removeRegion(region);
    }

    @Transactional
    @Override
    public void selectRegionForMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        Region region = regionService.getById(regionId);
        member.selectRegion(region);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberRegionInfos getRegionsOfMember(Long memberId) {
        Member member = memberService.getById(memberId);
        List<RegionInfo> regionInfos = toRegionsInfo(member.fetchRegions());
        return new MemberRegionInfos(
                member.getSelectedRegion().getId(),
                regionInfos
        );
    }
}

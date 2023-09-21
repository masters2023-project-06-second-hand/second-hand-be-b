package com.codesquad.secondhand.application.service.in;


import static com.codesquad.secondhand.application.service.in.region.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.adapter.in.web.response.MemberRegionInfos;
import com.codesquad.secondhand.adapter.in.web.response.RegionInfo;
import com.codesquad.secondhand.application.port.in.MyRegionsUseCase;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyRegionsService implements MyRegionsUseCase {

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

    @Transactional(readOnly = true)
    @Override
    public MemberRegionInfos getRegionsOfMember(Long memberId) {
        Member member = memberService.getById(memberId);
        List<Long> regionsId = member.fetchRegions();
        List<Region> regions = regionService.getAll(regionsId);
        List<RegionInfo> regionInfos = toRegionsInfo(regions);
        return new MemberRegionInfos(member.getSelectedRegionId(), regionInfos);
    }
}

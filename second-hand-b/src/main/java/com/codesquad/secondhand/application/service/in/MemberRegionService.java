package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.MemberRegionUseCase;
import com.codesquad.secondhand.application.port.in.response.MemberRegionInfos;
import com.codesquad.secondhand.application.port.in.response.RegionInfo;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public MemberRegionInfos getRegionsOfMember(Long memberId) {
        Member member = memberService.getById(memberId);
        List<RegionInfo> regionInfos = member.fetchRegions().stream()
                .map(region -> new RegionInfo(region.getId(), region.getName()))
                .collect(Collectors.toList());
        return new MemberRegionInfos(
                member.getSelectedRegion().getId(),
                regionInfos
        );
    }

    @Transactional
    @Override
    public void selectRegionForMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        Region region = regionService.getById(regionId);
        member.selectRegion(region);
    }
}

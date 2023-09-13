package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.adapter.in.web.response.MemberRegionInfos;
import com.codesquad.secondhand.adapter.in.web.response.RegionInfo;
import com.codesquad.secondhand.application.port.in.MemberRegionUseCase;
import com.codesquad.secondhand.application.port.in.exception.ExistsMemberRegionException;
import com.codesquad.secondhand.application.port.in.exception.NotExistsMemberRegionException;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;
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
        if (member.containsRegion(region)) {
            throw new ExistsMemberRegionException();
        }
        member.addRegion(region);
    }

    @Transactional
    @Override
    public void removeRegionFromMember(Long memberId, Long regionId) {
        Member member = memberService.getById(memberId);
        Region region = regionService.getById(regionId);
        if (!member.containsRegion(region)) {
            throw new NotExistsMemberRegionException();
        }
        member.removeRegion(region);
    }

    @Transactional(readOnly = true)
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
        if (!member.containsRegion(region)) {
            throw new NotExistsMemberRegionException();
        }
        member.selectRegion(region);
    }
}

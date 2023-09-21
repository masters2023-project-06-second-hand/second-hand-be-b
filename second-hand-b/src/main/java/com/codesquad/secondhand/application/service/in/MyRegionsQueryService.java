package com.codesquad.secondhand.application.service.in;


import static com.codesquad.secondhand.application.service.in.common.utils.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.adapter.in.web.query.member.response.MemberRegionInfos;
import com.codesquad.secondhand.adapter.in.web.common.response.RegionInfo;
import com.codesquad.secondhand.application.port.in.query.MyRegionsQueryUseCase;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyRegionsQueryService implements MyRegionsQueryUseCase {

    private final MemberService memberService;
    private final RegionService regionService;

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

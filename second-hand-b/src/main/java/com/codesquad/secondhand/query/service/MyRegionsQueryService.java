package com.codesquad.secondhand.query.service;


import static com.codesquad.secondhand.common.utils.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.region.Region;
import com.codesquad.secondhand.query.controller.common.response.RegionInfo;
import com.codesquad.secondhand.query.controller.member.response.MemberRegionInfos;
import com.codesquad.secondhand.query.port.MyRegionsQueryUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyRegionsQueryService implements MyRegionsQueryUseCase {

    private final MemberQueryService memberQueryService;
    private final RegionQueryService regionQueryService;

    @Transactional(readOnly = true)
    @Override
    public MemberRegionInfos getRegionsOfMember(Long memberId) {
        Member member = memberQueryService.getById(memberId);
        List<Long> regionsId = member.fetchRegions();
        List<Region> regions = regionQueryService.getAll(regionsId);
        List<RegionInfo> regionInfos = toRegionsInfo(regions);
        return new MemberRegionInfos(member.getSelectedRegionId(), regionInfos);
    }
}

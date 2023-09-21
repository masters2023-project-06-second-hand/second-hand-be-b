package com.codesquad.secondhand.adapter.in.web.query.member.response;

import com.codesquad.secondhand.adapter.in.web.query.common.response.RegionInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberRegionInfos {

    private final Long selectedRegionId;
    private final List<RegionInfo> regions;

    public MemberRegionInfos(Long selectedRegionId, List<RegionInfo> regions) {
        this.selectedRegionId = selectedRegionId;
        this.regions = regions;
    }
}

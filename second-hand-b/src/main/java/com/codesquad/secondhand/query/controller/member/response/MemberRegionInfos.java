package com.codesquad.secondhand.query.controller.member.response;

import com.codesquad.secondhand.query.controller.common.response.RegionInfo;
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

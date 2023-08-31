package com.codesquad.secondhand.application.port.in.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberRegionInfos {

    private Long selectedRegionId;
    private List<RegionInfo> regions;

    public MemberRegionInfos(Long selectedRegionId, List<RegionInfo> regions) {
        this.selectedRegionId = selectedRegionId;
        this.regions = regions;
    }
}

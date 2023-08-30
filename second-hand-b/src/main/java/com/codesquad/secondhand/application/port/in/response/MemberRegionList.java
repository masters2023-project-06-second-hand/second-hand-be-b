package com.codesquad.secondhand.application.port.in.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberRegionList {

    private Long selectedRegionId;
    private List<RegionInfo> regions;

    public MemberRegionList(Long selectedRegionId, List<RegionInfo> regions) {
        this.selectedRegionId = selectedRegionId;
        this.regions = regions;
    }
}

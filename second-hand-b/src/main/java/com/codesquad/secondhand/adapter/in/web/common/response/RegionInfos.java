package com.codesquad.secondhand.adapter.in.web.common.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RegionInfos {

    private final boolean hasNext;
    private final int page;
    private final List<RegionInfo> regions;

    public RegionInfos(boolean hasNext, int page, List<RegionInfo> regions) {
        this.hasNext = hasNext;
        this.page = page;
        this.regions = regions;
    }
}

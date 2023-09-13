package com.codesquad.secondhand.adapter.in.web.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RegionInfos {

    private boolean hasNext;
    private int page;
    private List<RegionInfo> regions;

    public RegionInfos(boolean hasNext, int page, List<RegionInfo> regions) {
        this.hasNext = hasNext;
        this.page = page;
        this.regions = regions;
    }
}

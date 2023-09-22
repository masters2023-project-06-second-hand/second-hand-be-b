package com.codesquad.secondhand.common.utils;

import com.codesquad.secondhand.query.controller.common.response.RegionInfo;
import com.codesquad.secondhand.command.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;

public class RegionMapper {

    private RegionMapper() {
        throw new UnsupportedOperationException();
    }

    public static List<RegionInfo> toRegionsInfo(List<Region> regions) {
        return regions.stream()
                .map(region -> new RegionInfo(region.getId(), region.getName()))
                .collect(Collectors.toUnmodifiableList());
    }
}

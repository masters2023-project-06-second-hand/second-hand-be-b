package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.RegionUseCase;
import com.codesquad.secondhand.application.port.in.exception.RegionNotFoundException;
import com.codesquad.secondhand.application.port.in.response.RegionInfo;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService implements RegionUseCase {

    private final RegionRepository regionRepository;

    public Region getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException();
                });
    }

    @Override
    public List<RegionInfo> searchRegionsByName(String word, Pageable pageable) {
        Slice<Region> regions = regionRepository.findByRegionsByName(word, pageable);
        return toRegionInfos(regions);
    }

    private List<RegionInfo> toRegionInfos(Slice<Region> regions) {
        return regions
                .map(region -> new RegionInfo(region.getId(), region.getName()))
                .getContent();
    }
}

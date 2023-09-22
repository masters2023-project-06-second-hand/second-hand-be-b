package com.codesquad.secondhand.query.service;

import static com.codesquad.secondhand.common.utils.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.command.domain.region.Region;
import com.codesquad.secondhand.command.port.out.RegionRepository;
import com.codesquad.secondhand.common.exception.RegionNotFoundException;
import com.codesquad.secondhand.query.controller.common.response.RegionInfo;
import com.codesquad.secondhand.query.controller.common.response.RegionInfos;
import com.codesquad.secondhand.query.port.RegionUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionQueryService implements RegionUseCase {

    private final RegionRepository regionRepository;

    public Region getById(long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException();
                });
    }

    @Override
    public RegionInfos searchRegionsByName(String word, Pageable pageable) {
        Slice<Region> regions = regionRepository.findByRegionsByName(word, pageable);
        return toRegionInfos(regions);
    }

    public void validateRegionId(long regionId) {
        if (regionRepository.existsById(regionId)) {
            return;
        }
        throw new RegionNotFoundException();
    }

    public List<Region> getAll(List<Long> regionsId) {
        return regionRepository.findAllById(regionsId);
    }

    private RegionInfos toRegionInfos(Slice<Region> regions) {
        List<RegionInfo> regionInfos = toRegionsInfo(regions.getContent());
        return new RegionInfos(regions.hasNext(), regions.getNumber(), regionInfos);
    }
}

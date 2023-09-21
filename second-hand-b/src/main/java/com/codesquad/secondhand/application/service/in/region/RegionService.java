package com.codesquad.secondhand.application.service.in.region;

import static com.codesquad.secondhand.application.service.in.region.RegionMapper.toRegionsInfo;

import com.codesquad.secondhand.adapter.in.web.response.RegionInfo;
import com.codesquad.secondhand.adapter.in.web.response.RegionInfos;
import com.codesquad.secondhand.application.port.in.RegionUseCase;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.application.service.in.exception.RegionNotFoundException;
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

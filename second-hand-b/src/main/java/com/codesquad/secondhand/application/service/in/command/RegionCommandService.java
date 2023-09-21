package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.application.service.in.common.exception.RegionNotFoundException;
import com.codesquad.secondhand.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionCommandService {

    private final RegionRepository regionRepository;

    public Region getById(long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException();
                });
    }


    public void validateRegionId(long regionId) {
        if (regionRepository.existsById(regionId)) {
            return;
        }
        throw new RegionNotFoundException();
    }
}

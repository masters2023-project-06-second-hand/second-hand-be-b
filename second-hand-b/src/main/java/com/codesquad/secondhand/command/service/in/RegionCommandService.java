package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.domain.region.Region;
import com.codesquad.secondhand.command.port.out.RegionRepository;
import com.codesquad.secondhand.common.exception.RegionNotFoundException;
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

package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.exception.RegionNotFoundException;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public Region getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException();
                });
    }

}

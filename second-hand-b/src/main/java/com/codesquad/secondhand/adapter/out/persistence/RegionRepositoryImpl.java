package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionJpaRepository;

    @Override
    public Optional<Region> findById(Long id) {
        return regionJpaRepository.findById(id);
    }

    @Override
    public void saveAll(List<Region> regions) {
        regionJpaRepository.saveAll(regions);
    }
}

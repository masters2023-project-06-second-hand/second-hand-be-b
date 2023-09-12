package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.RegionCrudRepository;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionCrudRepository regionCrudRepository;

    @Override
    public Optional<Region> findById(Long id) {
        return regionCrudRepository.findById(id);
    }

    @Override
    public void saveAll(List<Region> regions) {
        regionCrudRepository.saveAll(regions);
    }

    @Override
    public Slice<Region> findByRegionsByName(String word, Pageable pageable) {
        return regionCrudRepository.findByNameContains(word, pageable);
    }
}

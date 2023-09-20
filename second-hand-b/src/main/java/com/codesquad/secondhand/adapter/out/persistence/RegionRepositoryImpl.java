package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.RegionCrudRepository;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

    @Override
    public boolean existsById(long regionId) {
        return regionCrudRepository.existsById(regionId);
    }

    @Override
    public List<Region> findAllById(List<Long> regionsId) {
        Iterable<Region> regions = regionCrudRepository.findAllById(regionsId);
        return StreamSupport.stream(regions.spliterator(), false)
                .collect(Collectors.toList());
    }
}

package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RegionRepository {

    Optional<Region> findById(Long id);

    void saveAll(List<Region> regions);

    Slice<Region> findByRegionsByName(String word, Pageable pageable);
}

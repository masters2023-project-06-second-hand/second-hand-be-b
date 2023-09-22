package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.region.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RegionRepository {

    Optional<Region> findById(Long id);

    Slice<Region> findByRegionsByName(String word, Pageable pageable);

    boolean existsById(long regionId);

    List<Region> findAllById(List<Long> regionsId);
}

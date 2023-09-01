package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.Optional;

public interface RegionRepository {

    Optional<Region> findById(Long id);

    void saveAll(List<Region> regions);
}

package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.region.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface RegionJpaRepository extends CrudRepository<Region, Long> {

    Slice<Region> findByNameContains(String name, Pageable pageable);
}

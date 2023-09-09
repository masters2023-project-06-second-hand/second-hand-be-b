package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.region.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<Region, Long> {

    Slice<Region> findByNameContains(String name, Pageable pageable);
}

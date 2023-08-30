package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<Region, Long> {

}

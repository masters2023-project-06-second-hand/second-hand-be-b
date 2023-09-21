package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.region.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface RegionCrudRepository extends CrudRepository<Region, Long> {

    Slice<Region> findByNameContains(String name, Pageable pageable);
}

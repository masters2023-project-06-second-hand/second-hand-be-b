package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.response.RegionInfos;
import org.springframework.data.domain.Pageable;

public interface RegionUseCase {

    RegionInfos searchRegionsByName(String word, Pageable pageable);
}

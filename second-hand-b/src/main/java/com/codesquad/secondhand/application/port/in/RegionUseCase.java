package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.response.RegionInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RegionUseCase {

    List<RegionInfo> searchRegionsByName(String word, Pageable pageable);
}

package com.codesquad.secondhand.application.port.in.query;

import com.codesquad.secondhand.adapter.in.web.query.common.response.RegionInfos;
import org.springframework.data.domain.Pageable;

public interface RegionUseCase {

    /**
     * 페이지에 해당하는 지역 목록을 조회한다.
     *
     * @param word     검색할 지역명
     * @param pageable page와 size를 가지는 객체
     * @return 다음 페이지 여부, 페이지, 지역 목록 데이터를 가지는 객체
     */
    RegionInfos searchRegionsByName(String word, Pageable pageable);
}

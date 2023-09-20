package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.application.service.in.exception.ExistsMemberRegionException;
import com.codesquad.secondhand.application.service.in.exception.MinimumRegionRequirementException;
import com.codesquad.secondhand.application.service.in.exception.NotExistsMemberRegionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MyRegions {

    public static final int FIRST_INDEX = 0;
    public static final int MINIMUM_SIZE = 1;
    @ElementCollection
    private List<Long> regions = new ArrayList<>();

    public List<Long> getRegions() {
        return Collections.unmodifiableList(regions);
    }

    public void addRegion(long regionId) {
        if (regions.contains(regionId)) {
            throw new ExistsMemberRegionException();
        }
        regions.add(regionId);
    }

    public long removeRegion(long regionId) {
        if (!regions.contains(regionId)) {
            throw new NotExistsMemberRegionException();
        }
        if (regions.size() <= MINIMUM_SIZE) {
            throw new MinimumRegionRequirementException();
        }
        regions.remove(regionId);
        return regions.get(FIRST_INDEX);
    }

    public boolean contains(long regionId) {
        return regions.contains(regionId);
    }
}

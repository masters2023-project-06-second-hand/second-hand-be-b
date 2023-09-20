package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.application.service.in.exception.ExistsMemberRegionException;
import com.codesquad.secondhand.application.service.in.exception.MinimumRegionRequirementException;
import com.codesquad.secondhand.application.service.in.exception.NotExistsMemberRegionException;
import com.codesquad.secondhand.domain.region.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberRegions implements Serializable {

    public static final int FIRST_INDEX = 0;
    public static final int MINIMUM_SIZE = 1;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "member_region",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private List<Region> regions = new ArrayList<>();

    public List<Region> getRegions() {
        return Collections.unmodifiableList(regions);
    }

    public void addRegion(Region region) {
        if (regions.contains(region)) {
            throw new ExistsMemberRegionException();
        }
        regions.add(region);
    }

    public Region removeRegion(Region region) {
        if (!regions.contains(region)) {
            throw new NotExistsMemberRegionException();
        }
        if (regions.size() <= MINIMUM_SIZE) {
            throw new MinimumRegionRequirementException();
        }
        regions.remove(region);
        return regions.get(FIRST_INDEX);
    }

    public boolean contains(Region region) {
        return regions.contains(region);
    }
}

package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.region.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberRegions implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Region> regions = new ArrayList<>();

    public List<Region> getRegions() {
        return regions;
    }

    public void addRegion(Region region) {
        regions.add(region);
    }

    public void removeRegion(Region region) {
        regions.remove(region);
    }
}

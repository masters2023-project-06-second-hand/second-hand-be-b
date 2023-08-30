package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.region.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberRegions implements Serializable {

    @ElementCollection
    @CollectionTable(name = "member_region",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private List<Region> regions = new ArrayList<>();

    public void addRegion(Region region) {
        regions.add(region);
    }

    public void removeRegion(Region region) {
        regions.remove(region);
    }
}

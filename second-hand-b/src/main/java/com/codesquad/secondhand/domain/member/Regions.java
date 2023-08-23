package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Regions {
    @OneToMany(mappedBy = "id")
    private List<Region> regionList;
}

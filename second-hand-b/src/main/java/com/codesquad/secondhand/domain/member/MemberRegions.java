package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.memberregion.MemberRegion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberRegions implements Serializable {

    @OneToMany(mappedBy = "member")
    private List<MemberRegion> memberRegionList;
}

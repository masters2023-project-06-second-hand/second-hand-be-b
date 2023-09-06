package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.application.port.in.response.RegionInfo;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.region.Region;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    private String profileImage;
    @Embedded
    private MemberRegions myRegions = new MemberRegions();
    @ManyToOne
    @JoinColumn(name = "selected_region_id")
    private Region selectedRegion;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Likes likes = new Likes();

    public Member(String email, String nickname, String profileImage, Region region, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.myRegions.addRegion(region);
        this.selectedRegion = region;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public Role getRole() {
        return role;
    }

    public void addRegion(Region region) {
        myRegions.addRegion(region);
    }

    public void removeRegion(Region region) {
        myRegions.removeRegion(region);
    }

    public List<RegionInfo> fetchRegionInfos() {
        return myRegions.getRegions().stream()
                .map(region -> new RegionInfo(region.getId(), region.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void selectRegion(Region region) {
        this.selectedRegion = region;
    }


    public Collection<GrantedAuthority> getRoleAuthority() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getKey()));
    }

    public String getIdStringValue() {
        return String.valueOf(id);
    }

    public boolean addLikes(Product product) {
        return likes.add(product);
    }

    public boolean removeLikes(Product product) {
        return likes.remove(product);
    }

    public Set<Product> getProducts() {
        return likes.getProducts();
    }

    public Set<Product> getProductsByCategoryId(long categoryId) {
        return likes.getProductsByCategoryId(categoryId);
    }

    public boolean isSameId(long memberId) {
        return id.equals(memberId);
    }
}

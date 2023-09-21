package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.application.service.in.exception.NotExistsMemberRegionException;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    private String profileImage;
    @Embedded
    private MyRegions myRegions = new MyRegions();
    private long selectedRegionId;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Likes likes = new Likes();

    public Member(String email, String nickname, String profileImage, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
        this.likes = new Likes();
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

    public String getProfileImage() {
        return profileImage;
    }

    public long getSelectedRegionId() {
        return selectedRegionId;
    }

    public Role getRole() {
        return role;
    }

    public void addRegion(long regionId) {
        myRegions.addRegion(regionId);
    }

    public void removeRegion(long regionId) {
        this.selectedRegionId = myRegions.removeRegion(regionId);
    }

    public List<Long> fetchRegions() {
        return myRegions.getRegions();
    }

    public void selectRegion(long regionId) {
        if (!myRegions.contains(regionId)) {
            throw new NotExistsMemberRegionException();
        }
        this.selectedRegionId = regionId;
    }

    public String getIdStringValue() {
        return String.valueOf(id);
    }

    public boolean addLikes(long productId) {
        return likes.add(productId);
    }

    public boolean removeLikes(long productId) {
        return likes.remove(productId);
    }

    public boolean isSameId(long memberId) {
        return id.equals(memberId);
    }
}

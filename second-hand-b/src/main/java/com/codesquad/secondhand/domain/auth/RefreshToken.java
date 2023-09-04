package com.codesquad.secondhand.domain.auth;

import com.codesquad.secondhand.domain.member.Member;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenValue;
    private Instant expiryDate;
    @OneToOne
    private Member member;
    private String email;

    public RefreshToken(String tokenValue, Instant expiryDate, Member member, String email) {
        this.tokenValue = tokenValue;
        this.expiryDate = expiryDate;
        this.member = member;
        this.email = email;
    }
}

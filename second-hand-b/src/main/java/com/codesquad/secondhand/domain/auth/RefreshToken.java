package com.codesquad.secondhand.domain.auth;

import com.codesquad.secondhand.application.service.in.exception.InvalidRefreshTokenException;
import com.codesquad.secondhand.domain.member.Member;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;
    @OneToOne
    private Member member;
    private String email;

    public RefreshToken(String tokenValue, Date expireTime, Member member, String email) {
        this.tokenValue = tokenValue;
        this.expireTime = expireTime;
        this.member = member;
        this.email = email;
    }

    private boolean isSameTokenValue(String authentication) {
        return tokenValue.equals(authentication);
    }

    public void validate(String authentication) {
        if (!isSameTokenValue(authentication)) {
            throw new InvalidRefreshTokenException();
        }
    }
}

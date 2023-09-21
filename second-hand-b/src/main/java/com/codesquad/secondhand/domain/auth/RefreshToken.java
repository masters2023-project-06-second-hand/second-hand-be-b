package com.codesquad.secondhand.domain.auth;

import com.codesquad.secondhand.application.service.in.common.exception.InvalidRefreshTokenException;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private long memberId;
    private String email;

    public RefreshToken(String tokenValue, Date expireTime, long memberId, String email) {
        this.tokenValue = tokenValue;
        this.expireTime = expireTime;
        this.memberId = memberId;
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

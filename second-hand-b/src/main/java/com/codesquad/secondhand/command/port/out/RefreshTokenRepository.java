package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.auth.RefreshToken;
import java.util.Date;
import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    void deleteByExpireTimeBefore(Date now);

    Optional<RefreshToken> findByEmail(String email);

    void deleteByMemberId(long memberId);

    boolean existsByMemberId(long memberId);
}

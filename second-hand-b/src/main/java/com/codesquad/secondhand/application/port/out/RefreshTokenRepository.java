package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import java.util.Date;
import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    void deleteByExpireTimeBefore(Date now);

    Optional<RefreshToken> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}

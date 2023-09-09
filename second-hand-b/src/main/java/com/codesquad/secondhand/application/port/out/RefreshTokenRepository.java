package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import java.util.Date;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    void deleteByExpireTimeBefore(Date now);
}

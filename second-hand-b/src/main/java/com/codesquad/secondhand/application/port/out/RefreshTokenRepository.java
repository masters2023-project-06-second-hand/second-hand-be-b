package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.auth.RefreshToken;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);
}

package com.codesquad.secondhand.common.security.schedule;

import com.codesquad.secondhand.common.security.service.AuthService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthSchedule {

    private static final int REFRESH_TOKEN_CLEANUP_ROUND_TIME = 5000;
    private final AuthService authService;

    @Scheduled(fixedDelay = REFRESH_TOKEN_CLEANUP_ROUND_TIME)
    public void cleanupExpiredRefreshTokens() {
        Date now = new Date();
        authService.deleteByExpireTimeBefore(now);
    }

}

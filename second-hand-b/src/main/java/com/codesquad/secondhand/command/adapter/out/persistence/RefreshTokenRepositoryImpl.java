package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.RefreshTokenCrudRepository;
import com.codesquad.secondhand.command.domain.auth.RefreshToken;
import com.codesquad.secondhand.command.port.out.RefreshTokenRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenCrudRepository refreshTokenCrudRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenCrudRepository.save(refreshToken);
    }

    @Override
    public void deleteByExpireTimeBefore(Date date) {
        refreshTokenCrudRepository.deleteByExpireTimeBefore(date);
    }

    @Override
    public Optional<RefreshToken> findByEmail(String email) {
        return refreshTokenCrudRepository.findByEmail(email);
    }

    @Override
    public void deleteByMemberId(long memberId) {
        refreshTokenCrudRepository.deleteByMemberId(memberId);
    }

    @Override
    public boolean existsByMemberId(long memberId) {
        return refreshTokenCrudRepository.existsByMemberId(memberId);
    }
}

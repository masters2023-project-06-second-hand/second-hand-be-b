package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.auth.RefreshToken;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenCrudRepository extends CrudRepository<RefreshToken, Long> {

    void deleteByExpireTimeBefore(Date date);

    Optional<RefreshToken> findByEmail(String email);

    void deleteByMemberId(long memberId);

    boolean existsByMemberId(long memberId);
}

package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenCrudRepository extends CrudRepository<RefreshToken, Long> {

    void deleteByExpireTimeBefore(Date date);

    Optional<RefreshToken> findByEmail(String email);

    void deleteByEmail(String email);
}

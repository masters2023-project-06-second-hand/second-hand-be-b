package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByExpireTimeBefore(Date date);
}

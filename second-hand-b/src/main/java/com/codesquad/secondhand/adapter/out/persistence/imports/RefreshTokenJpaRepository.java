package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenJpaRepository extends CrudRepository<RefreshToken, Long> {

    void deleteByExpireTimeBefore(Date date);
}

package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

}

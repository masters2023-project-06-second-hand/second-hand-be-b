package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.member.Member;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MemberCrudRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}

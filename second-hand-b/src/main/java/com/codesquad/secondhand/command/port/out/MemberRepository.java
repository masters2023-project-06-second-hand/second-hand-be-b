package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.member.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    Member save(Member member);
}

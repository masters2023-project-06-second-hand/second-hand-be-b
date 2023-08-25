package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.member.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmail(String email);

    Member save(Member member);
}

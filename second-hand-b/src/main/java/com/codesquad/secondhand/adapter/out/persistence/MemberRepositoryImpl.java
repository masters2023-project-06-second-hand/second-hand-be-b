package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return userJpaRepository.save(member);
    }
}

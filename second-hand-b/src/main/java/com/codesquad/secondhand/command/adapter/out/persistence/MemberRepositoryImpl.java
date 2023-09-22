package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.MemberCrudRepository;
import com.codesquad.secondhand.command.port.out.MemberRepository;
import com.codesquad.secondhand.command.domain.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberCrudRepository memberCrudRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberCrudRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberCrudRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberCrudRepository.save(member);
    }
}

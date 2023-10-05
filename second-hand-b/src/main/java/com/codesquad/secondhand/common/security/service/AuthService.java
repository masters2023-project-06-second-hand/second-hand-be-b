package com.codesquad.secondhand.common.security.service;

import com.codesquad.secondhand.command.domain.auth.RefreshToken;
import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.command.port.out.RefreshTokenRepository;
import com.codesquad.secondhand.common.exception.InvalidRefreshTokenException;
import com.codesquad.secondhand.common.exception.MemberNotFoundException;
import com.codesquad.secondhand.common.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.common.security.port.AuthUseCase;
import com.codesquad.secondhand.common.security.request.SignUpRequest;
import com.codesquad.secondhand.common.security.response.Tokens;
import com.codesquad.secondhand.query.service.MemberQueryService;
import com.codesquad.secondhand.query.service.RegionQueryService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService implements AuthUseCase {

    private static final int REGIONS_FIRST_INDEX = 0;
    private static final int REGIONS_SECOND_INDEX = 1;
    private static final int SIZE_2 = 2;


    private final MemberQueryService memberQueryService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RegionQueryService regionQueryService;

    @Transactional
    @Override
    public Tokens signIn(String email) {
        Member member = getByEmail(email);
        return getTokens(email, member);
    }

    @Transactional
    @Override
    public Tokens signUp(String email, SignUpRequest signUpRequest) {
        List<Long> regionsId = signUpRequest.getRegionsId();
        Member member = memberQueryService.signUpMember(email, signUpRequest.getNickname(),
                signUpRequest.getProfileImg());
        long firstRegionId = regionsId.get(REGIONS_FIRST_INDEX);
        regionQueryService.validateRegionId(firstRegionId);
        member.addRegion(firstRegionId);
        member.selectRegion(firstRegionId);

        if (hasSecondRegion(regionsId)) {
            long secondRegion = regionsId.get(REGIONS_SECOND_INDEX);
            regionQueryService.validateRegionId(secondRegion);
            member.addRegion(secondRegion);
        }
        return getTokens(email, member);
    }

    @Override
    public Tokens getToken(String authentication) {
        Date now = new Date();
        if (!JwtTokenProvider.isValidRefreshToken(authentication, now)) {
            throw new InvalidRefreshTokenException();
        }
        String email = JwtTokenProvider.getEmailFromRefreshToken(authentication);
        RefreshToken refreshToken = getRefreshTokenByEmail(email);
        refreshToken.validate(authentication);
        Member member = memberQueryService.getByEmail(email);
        return getTokens(email, member);
    }

    public void deleteByExpireTimeBefore(Date now) {
        refreshTokenRepository.deleteByExpireTimeBefore(now);
    }

    private RefreshToken getRefreshTokenByEmail(String email) {
        return refreshTokenRepository.findByEmail(email)
                .orElseThrow(InvalidRefreshTokenException::new);
    }

    @Transactional
    @Override
    public void signOut(String validatedMemberId) {
        long memberId = Long.parseLong(validatedMemberId);
        if (refreshTokenRepository.existsByMemberId(memberId)) {
            refreshTokenRepository.deleteByMemberId(memberId);
        }
    }

    private Tokens getTokens(String email, Member member) {
        Date startDate = new Date();
        var accessToken = getAccessToken(email, member, startDate);
        var refreshToken = getRefreshToken(email, member, startDate);
        return new Tokens(accessToken, refreshToken, member.getId());
    }

    private String getAccessToken(String email, Member member, Date startDate) {
        return JwtTokenProvider.createAccessToken(email, member.getIdStringValue(), member.getRole().getKey(),
                startDate);
    }

    private String getRefreshToken(String email, Member member, Date startDate) {
        Date expiryDate = JwtTokenProvider.getRefreshTokenExpiryDate(startDate);
        String refreshToken = JwtTokenProvider.createRefreshToken(email, member.getIdStringValue(), startDate);
        refreshTokenRepository.save(new RefreshToken(refreshToken, expiryDate, member.getId(), email));
        return refreshToken;
    }

    private Member getByEmail(String email) {
        try {
            return memberQueryService.getByEmail(email);
        } catch (MemberNotFoundException e) {
            throw new NotRegisteredMemberException();
        }
    }

    private static boolean hasSecondRegion(List<Long> regionsId) {
        return regionsId.size() == SIZE_2;
    }
}

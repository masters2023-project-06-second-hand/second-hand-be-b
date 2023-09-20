package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.adapter.in.web.request.security.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.response.security.Tokens;
import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.port.out.RefreshTokenRepository;
import com.codesquad.secondhand.application.service.in.exception.InvalidRefreshTokenException;
import com.codesquad.secondhand.application.service.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.service.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.auth.RefreshToken;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
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


    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RegionService regionService;

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
        Member member = memberService.signUpMember(email, signUpRequest.getNickname(), signUpRequest.getProfileImg());
        Region firstRegion = regionService.getById(regionsId.get(REGIONS_FIRST_INDEX));
        member.addRegion(firstRegion);
        member.selectRegion(firstRegion);

        if (hasSecondRegion(regionsId)) {
            Region secondRegion = regionService.getById(regionsId.get(REGIONS_SECOND_INDEX));
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
        Member member = memberService.getByEmail(email);
        return getTokens(email, member);
    }

    public void deleteByExpireTimeBefore(Date now) {
        refreshTokenRepository.deleteByExpireTimeBefore(now);
    }

    private RefreshToken getRefreshTokenByEmail(String email) {
        return refreshTokenRepository.findByEmail(email)
                .orElseThrow(InvalidRefreshTokenException::new);
    }

    @Override
    public void signOut(Member member) {
        String email = member.getEmail();
        if (refreshTokenRepository.existsByEmail(email)) {
            refreshTokenRepository.deleteByEmail(email);
        }
    }

    private Tokens getTokens(String email, Member member) {
        Date startDate = new Date();
        var accessToken = getAccessToken(email, member.getIdStringValue(), startDate);
        var refreshToken = getRefreshToken(email, member, startDate);
        return new Tokens(accessToken, refreshToken, member.getId());
    }

    private String getAccessToken(String email, String id, Date startDate) {
        return JwtTokenProvider.createAccessToken(email, id, startDate);
    }

    private String getRefreshToken(String email, Member member, Date startDate) {
        Date expiryDate = JwtTokenProvider.getRefreshTokenExpiryDate(startDate);
        String refreshToken = JwtTokenProvider.createRefreshToken(email, member.getIdStringValue(), startDate);
        refreshTokenRepository.save(new RefreshToken(refreshToken, expiryDate, member, email));
        return refreshToken;
    }

    private Member getByEmail(String email) {
        try {
            return memberService.getByEmail(email);
        } catch (MemberNotFoundException e) {
            throw new NotRegisteredMemberException();
        }
    }

    private static boolean hasSecondRegion(List<Long> regionsId) {
        return regionsId.size() == SIZE_2;
    }
}

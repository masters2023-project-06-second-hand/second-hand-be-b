package com.codesquad.secondhand.utils;

import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.region.Region;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import io.restassured.RestAssured;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    public static final String AYAAN_EMAIL = "ayaan@email.com";
    public static final String AYAAN_NICKNAME = "이안";
    public static final String AYAAN_PROFILE_IMAGE = "url";
    public static final long AYAAN_DEFAULT_REGION_ID = 3L;
    public static final String ALBERT_EMAIL = "albert@email.com";
    public static final String ALBERT_NICKNAME = "앨버트";
    public static final String ALBERT_PROFILE_IMAGE = "url";
    public static final long ALBERT_DEFAULT_REGION_ID = 1L;
    public String ayaanAccessToken;
    public String albertAccessToken;
    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        initAccessToken();
    }

    private void initAccessToken() {
        Region ayyanRegion = regionRepository.findById(AYAAN_DEFAULT_REGION_ID).orElseThrow();
        Region albertRegion = regionRepository.findById(ALBERT_DEFAULT_REGION_ID).orElseThrow();
        Member ayaan = memberRepository.save(
                new Member(AYAAN_EMAIL, AYAAN_NICKNAME, AYAAN_PROFILE_IMAGE, ayyanRegion, Role.MEMBER));
        Member albert = memberRepository.save(
                new Member(ALBERT_EMAIL, ALBERT_NICKNAME, ALBERT_PROFILE_IMAGE, albertRegion, Role.MEMBER));
        final Date startDate = new Date();
        ayaanAccessToken = JwtTokenProvider.createAccessToken(AYAAN_EMAIL, ayaan.getIdStringValue(), startDate);
        albertAccessToken = JwtTokenProvider.createAccessToken(ALBERT_EMAIL, albert.getIdStringValue(), startDate);
    }

}

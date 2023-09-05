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

    public static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_NICKNAME = "이안";
    public static final String TEST_PROFILE_IMAGE = "url";
    public static final long TEST_DEFAULT_REGION_ID = 3L;
    public String accessToken;
    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        initAccessToken();
    }

    private void initAccessToken() {
        Region region = regionRepository.findById(TEST_DEFAULT_REGION_ID).orElseThrow();
        Member member = memberRepository.save(
                new Member(TEST_EMAIL, TEST_NICKNAME, TEST_PROFILE_IMAGE, region, Role.MEMBER));
        final Date startDate = new Date();
        accessToken = jwtTokenProvider.createAccessToken(TEST_EMAIL, member.getIdStringValue(), startDate);
    }

}

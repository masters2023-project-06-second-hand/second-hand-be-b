package com.codesquad.secondhand.utils;

import com.codesquad.secondhand.adapter.out.persistence.MemberJpaRepository;
import com.codesquad.secondhand.adapter.out.persistence.RegionJpaRepository;
import com.codesquad.secondhand.config.SecurityTestConfig;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.region.Region;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(value = SecurityTestConfig.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    public String accessToken;
    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private RegionJpaRepository regionJpaRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        Region region = regionJpaRepository.findById(3L).orElseThrow();
        memberJpaRepository.save(new Member("test@email.com", "이안", "url", region, Role.MEMBER));
        accessToken = jwtTokenProvider.createAccessToken("test@email.com", 1 + "");
    }

}

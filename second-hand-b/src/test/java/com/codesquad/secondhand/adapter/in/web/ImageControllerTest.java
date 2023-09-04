package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ImageControllerTest extends AcceptanceTest {

    @Test
    @DisplayName("이미지 업로드 요청을 받으면 이미지 아이디와 S3에 업로드한 이미지 URL을 반환한다.")
    void upload() throws IOException {
        //given
        String filePath = "/image/test.jpg";
        ClassPathResource resource = new ClassPathResource(filePath);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", resource.getFile())
                .header("Authorization", "Bearer " + accessToken)
                .when().post("/api/images")
                .then().log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(1L);
    }
}

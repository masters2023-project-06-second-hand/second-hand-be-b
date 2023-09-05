package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.ProductSteps.이미지를_삭제한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.이미지를_업로드한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

class ImageAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("이미지 업로드 요청을 받으면 이미지 아이디와 S3에 업로드한 이미지 URL을 반환한다.")
    void upload() throws IOException {
        //when
        String filePath = "/image/test.jpg";
        File file = new ClassPathResource(filePath).getFile();
        var response = 이미지를_업로드한다(file, accessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미지 삭제 요청을 받으면 이미지를 삭제하고 204 상태코드로 응답한다.")
    void delete() throws IOException {
        //given
        String filePath = "/image/test.jpg";
        File file = new ClassPathResource(filePath).getFile();
        var uploadImg = 이미지를_업로드한다(file, accessToken);
        Long imgId = uploadImg.jsonPath().getLong("id");

        //when
        var response = 이미지를_삭제한다(imgId, accessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}

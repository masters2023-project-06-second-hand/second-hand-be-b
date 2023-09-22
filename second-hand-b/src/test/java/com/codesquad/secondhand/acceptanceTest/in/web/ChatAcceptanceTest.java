package com.codesquad.secondhand.acceptanceTest.in.web;

import static com.codesquad.secondhand.acceptanceTest.in.web.ChatSteps.채팅방ID를_조회한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.ChatSteps.채팅방_정보를_조회한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.ProductSteps.상품을_등록한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChatAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setS3StorageService() {
        when(s3StorageAdapter.upload(any())).thenReturn("testUrl");
    }

    @Test
    @DisplayName("채팅방 ID를 조회하여 채팅방이 없으면 채팅방을 생성하고 채팅방 ID를 반환한다.")
    void getChattingRoomId() {
        출력_필드_추가("chat_getChattingRoomId", spec);

        // given
        Long productId = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");
        Long sellerId = 1L;

        // when
        var response = 채팅방ID를_조회한다(productId, sellerId, albertAccessToken, spec);

        // then
        assertThat(response.jsonPath().getLong("chatRoomId")).isEqualTo(1);
    }

    @Test
    @DisplayName("채팅방 ID로 채팅방 정보를 조회하여 반환한다.")
    void getChattingRoomDetail() {
        출력_필드_추가("chat_getChattingRoomDetail", spec);

        // given
        Long productId = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");
        Long sellerId = 1L;
        Long chatRoomId = 채팅방ID를_조회한다(productId, sellerId, albertAccessToken).jsonPath().getLong("chatRoomId");

        // when
        var response = 채팅방_정보를_조회한다(chatRoomId, albertAccessToken, spec);

        // then
        assertAll(
                () -> assertThat(response.jsonPath().getLong("product.id")).isEqualTo(productId),
                () -> assertThat(response.jsonPath().getString("opponentName")).isEqualTo(AYAAN_NICKNAME)
        );
    }
}

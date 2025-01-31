package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.command.port.out.ImageRepository;
import com.codesquad.secondhand.command.service.in.ImageCommandService;
import com.codesquad.secondhand.common.exception.ImageNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageCommandServiceTest {

    @InjectMocks
    ImageCommandService imageCommandService;

    @Mock
    ImageRepository imageRepository;

    @DisplayName("아이디로 이미지 조회시 DB에서 존재하지 않으면 예외를 던진다")
    @Test
    void getById() {
        // given
        long id = 1;
        given(imageRepository.findById(id)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> imageCommandService.getById(id))
                .isInstanceOf(ImageNotFoundException.class);
    }
}

package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.application.service.in.exception.RegionNotFoundException;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @InjectMocks
    RegionService regionService;

    @Mock
    RegionRepository regionRepository;

    @DisplayName("아이디로 Region을 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void getById() {
        // given
        long notExistsId = 1;
        given(regionRepository.findById(notExistsId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> regionService.getById(notExistsId))
                .isInstanceOf(RegionNotFoundException.class);
    }
}

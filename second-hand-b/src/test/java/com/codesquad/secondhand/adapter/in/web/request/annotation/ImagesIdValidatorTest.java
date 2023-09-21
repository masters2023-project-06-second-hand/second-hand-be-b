package com.codesquad.secondhand.adapter.in.web.request.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.adapter.in.web.command.product.annotation.ImagesIdValidator;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImagesIdValidatorTest {

    private ImagesIdValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ImagesIdValidator();
    }

    @DisplayName("리스트가 null일 경우 true 반환")
    @Test
    void whenListIsNull_thenIsValidReturnsTrue() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @DisplayName("리스트에 모든 ID가 정수일 경우 true 반환")
    @Test
    void whenListContainsOnlyPositiveIds_thenIsValidReturnsTrue() {
        List<Long> ids = List.of(1L, 2L, 3L);
        assertThat(validator.isValid(ids, null)).isTrue();
    }

    @DisplayName("리스트에 ID가 음수가 있을 경우 false 반환")
    @Test
    void whenListContainsNegativeId_thenIsValidReturnsFalse() {
        List<Long> ids = List.of(1L, -2L, 3L);
        assertThat(validator.isValid(ids, null)).isFalse();
    }

    @DisplayName("리스트에 ID가 0이 있을 경우 false 반환")
    @Test
     void whenListContainsZero_thenIsValidReturnsFalse() {
        List<Long> ids = List.of(1L, 0L, 3L);
        assertThat(validator.isValid(ids, null)).isFalse();
    }

    @DisplayName("빈 리스트 경우 true 반환")
    @Test
     void whenListIsEmpty_thenIsValidReturnsTrue() {
        List<Long> ids = Collections.emptyList();
        assertThat(validator.isValid(ids, null)).isTrue();
    }
}

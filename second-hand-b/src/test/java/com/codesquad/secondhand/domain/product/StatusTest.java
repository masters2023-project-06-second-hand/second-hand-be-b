package com.codesquad.secondhand.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class StatusTest {

    public static final String WRONG_NAME = "wrongName";

    @DisplayName("Status가 soldOut가 일 때 ture을 return한다")
    @Test
    void ifStatusIsSoldOutReturnTrue() {
        // given
        Status status = Status.SOLD_OUT;

        // then
        assertThat(Status.isSoldOut(status)).isTrue();
    }

    @DisplayName("Status가 soldOut가 아닐 때 false를 return한다.")
    @ParameterizedTest
    @EnumSource(value = Status.class, mode = EXCLUDE, names = "SOLD_OUT")
    void ifStatusIsNotSoldOutReturnFalsie() {
        // given
        Status status = Status.ON_SALES;

        // then
        assertThat(Status.isSoldOut(status)).isFalse();
    }

    @DisplayName("Status이름으로 Status찾아서 return한다")
    @ParameterizedTest
    @EnumSource(Status.class)
    void findByName(Status status) {
        assertThat(Status.findByName(status.getName())).isEqualTo(status);
    }
    @DisplayName("wrong한 이름으로 Status찾아서 예외가 발생한다")
    @Test
    void findByWrongName() {
        assertThatThrownBy(() -> Status.findByName(WRONG_NAME))
                .isInstanceOf(BadRequestException.class);
    }
}

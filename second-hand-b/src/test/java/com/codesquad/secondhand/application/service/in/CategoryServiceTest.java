package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.application.service.in.exception.CategoryNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryQueryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @DisplayName("조회하려는 카테고리 아이디가 DB에 없으면 예외를 던진다")
    @Test
    void getById() {
        // given
        Long id = 1L;
        given(categoryRepository.findById(id)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> categoryService.getById(id))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}

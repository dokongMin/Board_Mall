package com.dokong.board.web.service;

import com.dokong.board.domain.Category;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리_생성")
    public void saveCategory () throws Exception{
        // given
        CategoryDto categoryDto = getCategoryDto();
        CategoryDto categoryDtoEntity = categoryService.saveCategory(categoryDto);
        // when
        Category category = categoryService.findById(categoryDtoEntity.getId());
        // then
        assertThat(category.getCategoryName()).isEqualTo("과일");
     }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .categoryName("과일")
                .build();
    }
}
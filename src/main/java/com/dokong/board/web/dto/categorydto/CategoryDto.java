package com.dokong.board.web.dto.categorydto;

import com.dokong.board.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String categoryName;

    @Builder
    public CategoryDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category toEntity(){
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }

    public static CategoryDto of (Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}

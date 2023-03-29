package com.dokong.board.web.service;

import com.dokong.board.domain.Category;
import com.dokong.board.repository.CategoryRepository;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        if (isPresent(categoryDto)) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        } else {
        return CategoryDto.of(categoryRepository.save(categoryDto.toEntity()));
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    private boolean isPresent(CategoryDto categoryDto) {
        return categoryRepository.findByCategoryName(categoryDto.getCategoryName()).isPresent();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        });
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        });
    }
}

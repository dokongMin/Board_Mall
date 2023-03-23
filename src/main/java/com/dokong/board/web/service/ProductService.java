package com.dokong.board.web.service;

import com.dokong.board.domain.Category;
import com.dokong.board.domain.Product;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.product.UpdateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Transactional
    public SaveProductDto saveProduct(SaveProductDto saveProductDto, String categoryName) {
        Product product = productRepository.save(saveProductDto.toEntity());
        Category category = categoryService.findByCategoryName(categoryName);
        product.setCategory(category);
        return SaveProductDto.of(product);
    }

    @Transactional
    public UpdateProductDto updateProduct(Long id, UpdateProductDto productDto) {
        Product product = findById(id);
        product.updateProduct(productDto.getItemName(), productDto.getItemPrice(), productDto.getItemStock());
        return UpdateProductDto.of(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 상품은 존재하지 않습니다.");
        });
    }

}

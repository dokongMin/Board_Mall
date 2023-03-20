package com.dokong.board.web.service;

import com.dokong.board.domain.Product;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.SaveProductDto;
import com.dokong.board.web.dto.UpdateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public SaveProductDto saveProduct(SaveProductDto productDto) {
        return SaveProductDto.of(productRepository.save(productDto.toEntity()));
    }

    @Transactional
    public UpdateProductDto updateProduct(Long id, UpdateProductDto productDto) {
        Product product = checkExistProduct(id);
        product.updateProduct(productDto.getItemName(), productDto.getItemPrice(), productDto.getItemStock());
        return UpdateProductDto.of(product);
    }

    public Product checkExistProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 상품은 존재하지 않습니다.");
        });
    }
}

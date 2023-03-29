package com.dokong.board.web.service;

import com.dokong.board.domain.Category;
import com.dokong.board.domain.product.Product;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.product.DeleteProductRespDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.product.UpdateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Transactional
    public SaveProductDto saveProduct(SaveProductDto saveProductDto) {
        Category category = categoryService.findByCategoryName(saveProductDto.getCategoryName());
        Product product = productRepository.save(saveProductDto.toEntity());
        product.setCategory(category);
        return SaveProductDto.of(product);
    }

    @Transactional
    public UpdateProductDto updateProduct(Long id, UpdateProductDto productDto) {
        Product product = findById(id);
        product.updateProduct(productDto.getItemName(), productDto.getItemPrice(), productDto.getItemStock());
        return UpdateProductDto.of(product);
    }

    @Transactional
    public DeleteProductRespDto deleteProduct(Long id) {
        Product product = findById(id);
        product.deleteProduct();
        return DeleteProductRespDto.of(product);
    }


    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 상품은 존재하지 않습니다.");
        });
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}

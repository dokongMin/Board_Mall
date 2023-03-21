package com.dokong.board.web.service;

import com.dokong.board.domain.Category;
import com.dokong.board.domain.Product;
import com.dokong.board.repository.CategoryRepository;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.CategoryDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.product.UpdateProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;


    @Test
    @DisplayName("상품_생성")
    public void saveProduct() throws Exception {
        // given
        SaveProductDto productDto = getProduct();
        CategoryDto categoryDto = getCategoryDto();
        CategoryDto categoryEntity = categoryService.saveCategory(categoryDto);
        Category category = categoryService.findById(categoryEntity.getId());

        // when
        SaveProductDto saveProductDto = productService.saveProduct(productDto, category.getCategoryName());
        Product product = productService.findById(saveProductDto.getId());
        // then

        assertThat(product.getItemName()).isEqualTo("사과");
        assertThat(product.getItemStock()).isEqualTo(10);
        assertThat(product.getItemPrice()).isEqualTo(2000);
        assertThat(product.getCategory().getCategoryName()).isEqualTo("과일");
    }


    @Test
    @DisplayName("상품_수정")
    public void updateProduct() throws Exception {
        // given
        SaveProductDto productDto = getProduct();
        CategoryDto categoryDto = getCategoryDto();
        CategoryDto categoryEntity = categoryService.saveCategory(categoryDto);
        Category category = categoryService.findById(categoryEntity.getId());

        SaveProductDto saveProductDto = productService.saveProduct(productDto, category.getCategoryName());

        UpdateProductDto updateProductDto = UpdateProductDto.builder()
                .itemName("포도")
                .itemPrice(10000)
                .itemStock(1000)
                .build();
        // when
        Product product = productService.findById(saveProductDto.getId());
        productService.updateProduct(product.getId(), updateProductDto);
        // then
        assertThat(product.getItemName()).isEqualTo("포도");
        assertThat(product.getItemPrice()).isEqualTo(10000);
        assertThat(product.getItemStock()).isEqualTo(1000);
        assertThat(product.getCategory().getCategoryName()).isEqualTo("과일");
    }

    @Test
    @DisplayName("상품_수정_예외")
    public void updateProductException() throws Exception {
        SaveProductDto productDto = getProduct();
        CategoryDto categoryDto = getCategoryDto();
        CategoryDto categoryEntity = categoryService.saveCategory(categoryDto);
        Category category = categoryService.findById(categoryEntity.getId());

        SaveProductDto saveProductDto = productService.saveProduct(productDto, category.getCategoryName());

        UpdateProductDto updateProductDto = UpdateProductDto.builder()
                .itemName("포도")
                .itemPrice(10000)
                .itemStock(1000)
                .build();
        // then
        assertThatThrownBy(() -> productService.updateProduct(100L, updateProductDto))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 상품은 존재하지 않습니다.");
    }

    private SaveProductDto getProduct() {
        return SaveProductDto.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();
    }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .categoryName("과일")
                .build();
    }

}
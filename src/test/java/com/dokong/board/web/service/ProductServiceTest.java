package com.dokong.board.web.service;

import com.dokong.board.domain.Product;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.SaveProductDto;
import com.dokong.board.web.dto.UpdateProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품_생성")
    public void saveProduct () throws Exception{
        // given
        SaveProductDto productDto = getProduct();

        // when
        productService.saveProduct(productDto);
        // then
        assertThat(productRepository.findAll().get(0).getItemName()).isEqualTo("사과");
        assertThat(productRepository.findAll().get(0).getItemStock()).isEqualTo(10);
        assertThat(productRepository.findAll().get(0).getItemPrice()).isEqualTo(2000);

     }

     @Test
     @DisplayName("상품_수정")
     public void updateProduct () throws Exception{
         // given
         SaveProductDto productDto = getProduct();
         productService.saveProduct(productDto);

         UpdateProductDto updateProductDto = UpdateProductDto.builder()
                 .itemName("포도")
                 .itemPrice(10000)
                 .itemStock(1000)
                 .build();
         // when
         productService.updateProduct(productRepository.findAll().get(0).getId(), updateProductDto);
         // then
         assertThat(productRepository.findAll().get(0).getItemName()).isEqualTo("포도");
         assertThat(productRepository.findAll().get(0).getItemPrice()).isEqualTo(10000);
         assertThat(productRepository.findAll().get(0).getItemStock()).isEqualTo(1000);
      }
      
      @Test
      @DisplayName("상품_수정_예외")
      public void updateProductException () throws Exception{
          SaveProductDto productDto = getProduct();
          productService.saveProduct(productDto);

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
}
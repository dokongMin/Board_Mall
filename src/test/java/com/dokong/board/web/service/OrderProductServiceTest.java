package com.dokong.board.web.service;

import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class OrderProductServiceTest {


    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("주문_상품_저장")
    public void saveOrderProduct () throws Exception{
        // given
        CategoryDto getCategoryDto = getCategoryDto();
        CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

        SaveProductDto getSaveProductDto = getSaveProductDto();
        SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto, categoryDto.getCategoryName());
        Product product = productService.findById(saveProductDto.getId());

        SaveOrderProductDto saveOrderProductDto = getSaveOrderProductDto();
        SaveOrderProductDto saveOrderProductDtoEntity = orderProductService.saveOrderProduct(saveOrderProductDto, product.getId());
        OrderProduct orderProduct = orderProductService.findById(saveOrderProductDtoEntity.getId());

        // then
        assertThat(orderProduct.getOrderItemPrice()).isEqualTo(10000);
        assertThat(orderProduct.getOrderItemCount()).isEqualTo(10);
        assertThat(product.getItemStock()).isEqualTo(90);
     }

     @Test
     @DisplayName("주문_상품_취소")
     public void cancelOrderProduct () throws Exception{
         CategoryDto getCategoryDto = getCategoryDto();
         CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

         SaveProductDto getSaveProductDto = getSaveProductDto();
         SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto, categoryDto.getCategoryName());
         Product product = productService.findById(saveProductDto.getId());

         SaveOrderProductDto saveOrderProductDto = getSaveOrderProductDto();
         SaveOrderProductDto saveOrderProductDtoEntity = orderProductService.saveOrderProduct(saveOrderProductDto, product.getId());
         OrderProduct orderProduct = orderProductService.findById(saveOrderProductDtoEntity.getId());
         //when
         orderProductService.cancelOrderProduct(orderProduct.getId());
         // then
         assertThat(product.getItemStock()).isEqualTo(100);
         assertThat(product.getOrderProducts().size()).isEqualTo(0);
         assertThat(orderProductRepository.findAll().size()).isEqualTo(0);
      }

    private SaveOrderProductDto getSaveOrderProductDto() {
        return SaveOrderProductDto.builder()
                .orderItemPrice(10000)
                .orderItemCount(10)
                .build();
    }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .categoryName("과일")
                .build();
    }

    private SaveProductDto getSaveProductDto() {
        return SaveProductDto.builder()
                .itemName("사과")
                .itemPrice(10000)
                .itemStock(100)
                .build();
    }
}
//package com.dokong.board.domain;
//
//import com.dokong.board.web.dto.categorydto.CategoryDto;
//import com.dokong.board.web.dto.product.SaveProductDto;
//import com.dokong.board.web.service.CategoryService;
//import com.dokong.board.web.service.ProductService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//public class ItemStockSynchronizedTest {
//
//    @Autowired
//    ProductService productService;
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Test
//    @DisplayName("Sync_재고_동시성_테스트")
//    public void sync_request () throws Exception{
//
//        CategoryDto categoryDto = CategoryDto.builder()
//                .categoryName("과일")
//                .build();
//        CategoryDto categoryDtoEntity = categoryService.saveCategory(categoryDto);
//        SaveProductDto saveProductDto = SaveProductDto.builder()
//                .itemName("사과")
//                .itemStock(1000)
//                .itemPrice(1000)
//                .build();
//        SaveProductDto saveProductDtoEntity = productService.saveProduct(saveProductDto, categoryDtoEntity.getCategoryName());
//        Product product = productService.findById(saveProductDtoEntity.getId());
//
//        int threadCount = 1000;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try{
//                    product.removeStock(1);
//                }
//                finally {
//                    latch.countDown();
//                }
//            });
//
//        }
//        latch.await();
//        assertThat(product.getItemStock()).isEqualTo(0);
//     }
//}

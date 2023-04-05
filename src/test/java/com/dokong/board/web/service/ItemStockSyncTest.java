//package com.dokong.board.web.service;
//
//import com.dokong.board.domain.Address;
//import com.dokong.board.domain.OrderProduct;
//import com.dokong.board.domain.product.Product;
//import com.dokong.board.domain.order.Order;
//import com.dokong.board.repository.OrderProductRepository;
//import com.dokong.board.repository.OrderRepository;
//import com.dokong.board.repository.product.ProductRepository;
//import com.dokong.board.web.dto.categorydto.CategoryDto;
//import com.dokong.board.web.dto.coupondto.AddCouponDto;
//import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
//import com.dokong.board.web.dto.orderdto.SaveOrderDto;
//import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
//import com.dokong.board.web.dto.product.SaveProductDto;
//import com.dokong.board.web.dto.userdto.JoinUserDto;
//import com.dokong.board.web.dto.logindto.LoginUserDto;
//import com.dokong.board.web.dto.userdto.SessionUserDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//
//public class ItemStockSyncTest {
//
//    @Autowired
//    OrderService orderService;
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    LoginService loginService;
//
//
//    @Autowired
//    ProductService productService;
//    @Autowired
//    CategoryService categoryService;
//    @Autowired
//    OrderProductService orderProductService;
//
//    @Autowired
//    DeliveryService deliveryService;
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    OrderProductRepository orderProductRepository;
//
//    @Autowired
//    OrderRepository orderRepository;
//    @Autowired
//    CouponService couponService;
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    @DisplayName("sync_트랜잭션_테스트")
//    @Transactional
//    public void sync_transactional_test () throws Exception{
//        // given
//        JoinUserDto joinUserDto = getJoinUserDto();
//        userService.saveUser(joinUserDto);
//
//        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
//        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
//
//        CategoryDto getCategoryDto = getCategoryDto();
//        CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);
//
//        SaveProductDto getSaveProductDto = getSaveProductDto();
//        SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto, categoryDto.getCategoryName());
//        int threadCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//        SaveOrderProductDto saveOrderProductDto = getSaveOrderProductDto();
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try{
//                    orderProductService.saveOrderProduct(sessionUserDto, saveOrderProductDto, saveProductDto.getId());
//                }
//                finally {
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//        assertThat(productService.findById(saveProductDto.getId()).getItemStock()).isEqualTo(0);
//    }
//
//    private SaveOrderProductDto getSaveOrderProductDto() {
//        return SaveOrderProductDto.builder()
//                .orderItemPrice(10000)
//                .orderItemCount(100)
//                .build();
//    }
//
//    private CategoryDto getCategoryDto() {
//        return CategoryDto.builder()
//                .categoryName("과일")
//                .build();
//    }
//
//    private SaveProductDto getSaveProductDto() {
//        return SaveProductDto.builder()
//                .itemName("사과")
//                .itemPrice(10000)
//                .itemStock(20000)
//                .build();
//    }
//    private LoginUserDto getLoginUserDto(JoinUserDto joinUserDto) {
//        return LoginUserDto.builder()
//                .username(joinUserDto.getUsername())
//                .password(joinUserDto.getPassword())
//                .build();
//    }
//
//    private JoinUserDto getJoinUserDto() {
//        return JoinUserDto.builder()
//                .username("aaa")
//                .password("bbb")
//                .build();
//    }
//}

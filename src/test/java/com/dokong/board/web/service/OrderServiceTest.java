package com.dokong.board.web.service;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.exception.AlreadyDeliverException;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.repository.OrderRepository;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;


    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderProductService orderProductService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderRepository orderRepository;


    @Test
    @DisplayName("주문_생성")
    public void creatOrder () throws Exception{
        // given
        Address address = new Address("서울시", "000-000", "0000");

        /**
         * 유저 정보 저장 & 로그인
         */
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        /**
         * 카테고리 저장 & 상품 저장
         */
        CategoryDto categoryDto = getCategoryDto();
        categoryService.saveCategory(categoryDto);

        SaveProductDto saveProductDto = getSaveProductDtoApple();
        SaveProductDto saveProductDtoEntity = productService.saveProduct(saveProductDto, categoryDto.getCategoryName());
        Product productApple = productService.findById(saveProductDtoEntity.getId());

        SaveProductDto saveProductDtoGrape = getSaveProductDtoGrape();
        SaveProductDto saveProductDtoEntityGrape = productService.saveProduct(saveProductDtoGrape, categoryDto.getCategoryName());
        Product productGrape = productService.findById(saveProductDtoEntityGrape.getId());

        List<Product> productList = productRepository.findAll();
        List<Long> productListId = productList.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        /**
         * 주문 상품 생성
         */
        SaveOrderProductDto orderProductDtoApple = getOrderProductDtoApple();
        SaveOrderProductDto orderProductDtoGrape = getOrderProductDtoGrape();
        List<SaveOrderProductDto> orderProductListDto = new ArrayList<>();
        orderProductListDto.add(orderProductDtoApple);
        orderProductListDto.add(orderProductDtoGrape);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
        /**
         * 주문 정보 저장 & 연관관계 편의 메소드 실행
         */
        SaveOrderDto saveOrderDto = getSaveOrderDto(address);
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto, sessionUserDto, saveDeliveryDtoEntity, productListId, orderProductListDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE);
        assertThat(order.getOrderProducts().get(0).getOrderItemCount()).isEqualTo(10);

        List<OrderProduct> allOrderProduct = orderProductRepository.findAll();
        assertThat(allOrderProduct.get(0).getOrder().getId()).isEqualTo(order.getId());
        assertThat(allOrderProduct.get(1).getOrder().getId()).isEqualTo(order.getId());

     }

    @Test
    @DisplayName("주문_취소")
    public void cancelOrder () throws Exception{
        // given
        Address address = new Address("서울시", "000-000", "0000");

        /**
         * 유저 정보 저장 & 로그인
         */
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        /**
         * 카테고리 저장 & 상품 저장
         */
        CategoryDto categoryDto = getCategoryDto();
        categoryService.saveCategory(categoryDto);

        SaveProductDto saveProductDto = getSaveProductDtoApple();
        SaveProductDto saveProductDtoEntity = productService.saveProduct(saveProductDto, categoryDto.getCategoryName());
        Product productApple = productService.findById(saveProductDtoEntity.getId());

        SaveProductDto saveProductDtoGrape = getSaveProductDtoGrape();
        SaveProductDto saveProductDtoEntityGrape = productService.saveProduct(saveProductDtoGrape, categoryDto.getCategoryName());
        Product productGrape = productService.findById(saveProductDtoEntityGrape.getId());

        List<Product> productList = productRepository.findAll();
        List<Long> productListId = productList.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        /**
         * 주문 상품 생성
         */
        SaveOrderProductDto orderProductDtoApple = getOrderProductDtoApple();
        SaveOrderProductDto orderProductDtoGrape = getOrderProductDtoGrape();
        List<SaveOrderProductDto> orderProductListDto = new ArrayList<>();
        orderProductListDto.add(orderProductDtoApple);
        orderProductListDto.add(orderProductDtoGrape);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
        /**
         * 주문 정보 저장 & 연관관계 편의 메소드 실행
         */
        SaveOrderDto saveOrderDto = getSaveOrderDto(address);
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto, sessionUserDto, saveDeliveryDtoEntity, productListId, orderProductListDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());
        orderService.cancelOrder(order.getId());


        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
        assertThat(productList.get(0).getOrderProducts()).isEmpty();
    }
    @Test
    @DisplayName("주문_취소_예외")
    public void cancelOrderException () throws Exception{
        // given
        Address address = new Address("서울시", "000-000", "0000");

        /**
         * 유저 정보 저장 & 로그인
         */
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        /**
         * 카테고리 저장 & 상품 저장
         */
        CategoryDto categoryDto = getCategoryDto();
        categoryService.saveCategory(categoryDto);

        SaveProductDto saveProductDto = getSaveProductDtoApple();
        SaveProductDto saveProductDtoEntity = productService.saveProduct(saveProductDto, categoryDto.getCategoryName());
        Product productApple = productService.findById(saveProductDtoEntity.getId());

        SaveProductDto saveProductDtoGrape = getSaveProductDtoGrape();
        SaveProductDto saveProductDtoEntityGrape = productService.saveProduct(saveProductDtoGrape, categoryDto.getCategoryName());
        Product productGrape = productService.findById(saveProductDtoEntityGrape.getId());

        List<Product> productList = productRepository.findAll();
        List<Long> productListId = productList.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        /**
         * 주문 상품 생성
         */
        SaveOrderProductDto orderProductDtoApple = getOrderProductDtoApple();
        SaveOrderProductDto orderProductDtoGrape = getOrderProductDtoGrape();
        List<SaveOrderProductDto> orderProductListDto = new ArrayList<>();
        orderProductListDto.add(orderProductDtoApple);
        orderProductListDto.add(orderProductDtoGrape);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
        Delivery delivery = deliveryService.findById(saveDeliveryDtoEntity.getId());
        /**
         * 주문 정보 저장 & 연관관계 편의 메소드 실행
         */
        SaveOrderDto saveOrderDto = getSaveOrderDto(address);
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto, sessionUserDto, saveDeliveryDtoEntity, productListId, orderProductListDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());

        /**
         * 배송 상태 변경
         */
        delivery.updateDeliveryStatus(DeliveryStatus.DELIVER_PROCEED);
        // then
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.DELIVER_PROCEED);
        assertThatThrownBy(() -> orderService.cancelOrder(order.getId()))
                .isExactlyInstanceOf(AlreadyDeliverException.class)
                .hasMessageContaining("배송 중이거나 배송 완료 된 제품은 주문 취소가 불가합니다.");
    }



    private SaveDeliveryDto getSaveDeliveryDto(Address address) {
        return SaveDeliveryDto.builder()
                .address(address)
                .build();
    }

    private SaveOrderProductDto getOrderProductDtoApple() {
        return SaveOrderProductDto.builder()
                .orderItemCount(10)
                .orderItemPrice(2000)
                .build();
    }
    private SaveOrderProductDto getOrderProductDtoGrape() {
        return SaveOrderProductDto.builder()
                .orderItemCount(20)
                .orderItemPrice(4000)
                .build();
    }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .categoryName("과일")
                .build();
    }

    private SaveProductDto getSaveProductDtoApple() {
        return SaveProductDto.builder()
                .itemName("사과")
                .itemPrice(2000)
                .itemStock(100)
                .build();
    }
    private SaveProductDto getSaveProductDtoGrape() {
        return SaveProductDto.builder()
                .itemName("포도")
                .itemPrice(4000)
                .itemStock(100)
                .build();
    }

    private LoginUserDto getLoginUserDto(JoinUserDto joinUserDto) {
        return LoginUserDto.builder()
                .username(joinUserDto.getUsername())
                .password(joinUserDto.getPassword())
                .build();
    }

    private JoinUserDto getJoinUserDto() {
        return JoinUserDto.builder()
                .username("aaa")
                .password("bbb")
                .build();
    }

    private SaveOrderDto getSaveOrderDto(Address address) {
        return SaveOrderDto.builder()
                .address(address)
                .build();
    }
}
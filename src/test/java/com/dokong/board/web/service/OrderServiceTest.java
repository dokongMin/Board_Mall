package com.dokong.board.web.service;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.exception.AlreadyDeliverException;
import com.dokong.board.exception.CouponMinPriceException;
import com.dokong.board.exception.NotEnoughStockException;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.repository.OrderRepository;
import com.dokong.board.repository.ProductRepository;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.logindto.LoginUserDto;
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
    @Autowired
    CouponService couponService;


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
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDto1(sessionUserDto, productListId);
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE);
        assertThat(order.getOrderProducts().get(0).getOrderItemCount()).isEqualTo(10);
        assertThat(order.getOrderProducts().get(1).getOrderItemCount()).isEqualTo(20);
//
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
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDto1(sessionUserDto, productListId);
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());
        order.cancelOrder();


        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
        assertThat(order.getOrderProducts().get(0).getProduct().getItemStock()).isEqualTo(100);
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
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDto1(sessionUserDto, productListId);
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();
        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());

        /**
         * 배송 상태 변경
         */
        order.updateOrderStatus(OrderStatus.PAY_COMPLETE);
        order.getDelivery().updateDeliveryStatus(DeliveryStatus.DELIVER_PROCEED);
        // then
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.DELIVER_PROCEED);
        assertThatThrownBy(() -> orderService.cancelOrder(order.getId()))
                .isExactlyInstanceOf(AlreadyDeliverException.class)
                .hasMessageContaining("배송 중이거나 배송 완료 된 제품은 주문 취소가 불가합니다.");
    }
    @Test
    @DisplayName("상품_수량_부족_예외")
    public void notEnoughItemCountException () throws Exception{
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
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDto1Ex(sessionUserDto, productListId);
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();

        // then

        assertThatThrownBy(() -> orderService.saveOrder(saveOrderDto))
                .isExactlyInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("수량이 부족합니다.");
    }

    @Test
    @DisplayName("쿠폰_적용_주문_생성")
    public void createOrderIncludeCoupon () throws Exception{
        // given
        Address address = new Address("서울시", "000-000", "0000");

        /**
         * 유저 정보 저장 & 로그인
         */
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        AddCouponDto addCouponDto = getCoupon(sessionUserDto);
        AddCouponResponseDto addCouponResponseDto = couponService.addCoupon(addCouponDto);

        /**
         * 카테고리 저장 & 상품 저장
         */
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDtoCoupon(sessionUserDto, productListId, addCouponResponseDto.getId());
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();

        SaveOrderDto saveOrderDtoEntity = orderService.saveOrder(saveOrderDto);
        Order order = orderService.findById(saveOrderDtoEntity.getId());

        // then
        assertThat(order.getOrderProducts().get(0).getOrderItemPrice()).isEqualTo(8910);
        assertThat(order.getOrderProducts().get(1).getOrderItemPrice()).isEqualTo(36000);
    }



    @Test
    @DisplayName("쿠폰_적용_주문_생성_예외")
    public void createOrderIncludeCouponException () throws Exception{
        // given
        Address address = new Address("서울시", "000-000", "0000");

        /**
         * 유저 정보 저장 & 로그인
         */
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        AddCouponDto addCouponDto = getCoupon(sessionUserDto);
        AddCouponResponseDto addCouponResponseDto = couponService.addCoupon(addCouponDto);


        /**
         * 카테고리 저장 & 상품 저장
         */
        List<Long> productListId = getProductListId();
        /**
         * 주문 상품 생성
         */

        List<SaveOrderProductDto> saveOrderProductDtos = new ArrayList<>();
        SaveOrderProductDto saveOrderProductDto1 = getSaveOrderProductDtoCouponEx(sessionUserDto, productListId, addCouponResponseDto.getId());
        SaveOrderProductDto saveOrderProductDto2 = getSaveOrderProductDto2(sessionUserDto, productListId);
        saveOrderProductDtos.add(saveOrderProductDto1);
        saveOrderProductDtos.add(saveOrderProductDto2);

        /**
         * 배송 정보 저장
         */
        SaveDeliveryDto saveDeliveryDto = getSaveDeliveryDto(address);
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);
//        /**
//         * 주문 정보 저장 & 연관관계 편의 메소드 실행
//         */
        SaveOrderDto saveOrderDto = SaveOrderDto.builder()
                .address(address)
                .userId(sessionUserDto.getId())
                .saveDeliveryDto(saveDeliveryDtoEntity)
                .saveOrderProductDtos(saveOrderProductDtos)
                .build();


              // then
        assertThatThrownBy(() ->orderService.saveOrder(saveOrderDto))
                .isExactlyInstanceOf(CouponMinPriceException.class)
                .hasMessageContaining("쿠폰을 사용하기 위해서는 최소 10,000 원 이상 구매해야 합니다.");
    }

    private AddCouponDto getCoupon(SessionUserDto sessionUserDto) {
        return AddCouponDto.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponDetail("회원가입을 축하하여 드리는 쿠폰입니다.")
                .couponRate(10)
                .minCouponPrice(10000)
                .userId(sessionUserDto.getId())
                .build();
    }
    private SaveOrderProductDto getSaveOrderProductDto2(SessionUserDto sessionUserDto, List<Long> productListId) {
        SaveOrderProductDto saveOrderProductDto2 = SaveOrderProductDto.builder()
                .orderItemPrice(2000)
                .orderItemCount(20)
                .userId(sessionUserDto.getId())
                .productId(productListId.get(1))
                .build();
        return saveOrderProductDto2;
    }

    private SaveOrderProductDto getSaveOrderProductDto1(SessionUserDto sessionUserDto, List<Long> productListId) {
        SaveOrderProductDto saveOrderProductDto1 = SaveOrderProductDto.builder()
                .orderItemPrice(1000)
                .orderItemCount(10)
                .userId(sessionUserDto.getId())
                .productId(productListId.get(0))
                .build();
        return saveOrderProductDto1;
    }
    private SaveOrderProductDto getSaveOrderProductDtoCoupon(SessionUserDto sessionUserDto, List<Long> productListId, Long couponId) {
        SaveOrderProductDto saveOrderProductDto1 = SaveOrderProductDto.builder()
                .orderItemPrice(1000)
                .orderItemCount(11)
                .userId(sessionUserDto.getId())
                .productId(productListId.get(0))
                .couponId(couponId)
                .build();
        return saveOrderProductDto1;
    }
    private SaveOrderProductDto getSaveOrderProductDtoCouponEx(SessionUserDto sessionUserDto, List<Long> productListId, Long couponId) {
        SaveOrderProductDto saveOrderProductDto1 = SaveOrderProductDto.builder()
                .orderItemPrice(1000)
                .orderItemCount(9)
                .userId(sessionUserDto.getId())
                .productId(productListId.get(0))
                .couponId(couponId)
                .build();
        return saveOrderProductDto1;
    }

    private SaveOrderProductDto getSaveOrderProductDto1Ex(SessionUserDto sessionUserDto, List<Long> productListId) {
        SaveOrderProductDto saveOrderProductDto1 = SaveOrderProductDto.builder()
                .orderItemPrice(1000)
                .orderItemCount(1000)
                .userId(sessionUserDto.getId())
                .productId(productListId.get(0))
                .build();
        return saveOrderProductDto1;
    }

    private SaveDeliveryDto getSaveDeliveryDto(Address address) {
        return SaveDeliveryDto.builder()
                .address(address)
                .build();
    }

    private SaveOrderProductDto getOrderProductDtoAppleExCoupon() {
        return SaveOrderProductDto.builder()
                .orderItemCount(5)
                .orderItemPrice(1200)
                .build();
    }
    private SaveOrderProductDto getOrderProductDtoGrapeExCoupon() {
        return SaveOrderProductDto.builder()
                .orderItemCount(5)
                .orderItemPrice(1500)
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
                .categoryName("과일")
                .build();
    }
    private SaveProductDto getSaveProductDtoGrape() {
        return SaveProductDto.builder()
                .itemName("포도")
                .itemPrice(4000)
                .itemStock(100)
                .categoryName("과일")
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

    private List<Long> getProductListId() {
        CategoryDto categoryDto = getCategoryDto();
        categoryService.saveCategory(categoryDto);

        SaveProductDto saveProductDto = getSaveProductDtoApple();
        SaveProductDto saveProductDtoEntity = productService.saveProduct(saveProductDto);
        productService.findById(saveProductDtoEntity.getId());

        SaveProductDto saveProductDtoGrape = getSaveProductDtoGrape();
        SaveProductDto saveProductDtoEntityGrape = productService.saveProduct(saveProductDtoGrape);
        productService.findById(saveProductDtoEntityGrape.getId());

        List<Product> productList = productRepository.findAll();
        List<Long> productListId = productList.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        return productListId;
    }
}
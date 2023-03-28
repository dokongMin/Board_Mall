package com.dokong.board.web.service;

import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.OrderRepository;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderproductdto.OrderProductRespDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final DeliveryService deliveryService;
    private final OrderProductService orderProductService;

    private final CouponService couponService;
    private final ProductService productService;

    @Transactional
    public SaveOrderDto saveOrder(SaveOrderDto saveOrderDto, SessionUserDto sessionUserDto, SaveDeliveryDto deliveryDto,
                                  List<Long> productIds, List<SaveOrderProductDto> saveOrderProductDtos) {
        Order order = orderRepository.save(saveOrderDto.toEntity());
        User user = userService.findById(sessionUserDto.getId());
        Delivery delivery = deliveryService.findById(deliveryDto.getId());
        List<OrderProduct> orderProducts = getOrderProducts(sessionUserDto, productIds, saveOrderProductDtos);
        order.createOrder(user, delivery, orderProducts);
        return SaveOrderDto.of(order);
    }


    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findById(orderId);
        order.cancelOrder();
    }

    private List<OrderProduct> getOrderProducts(SessionUserDto sessionUserDto, List<Long> productIds, List<SaveOrderProductDto> saveOrderProductDtos) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            SaveOrderProductDto orderProductDto = saveOrderProductDtos.get(i);
            SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(sessionUserDto, orderProductDto, productId);
            OrderProduct orderProduct = orderProductService.findById(saveOrderProductDto.getId());
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

    /**
     * 쿠폰 적용 주문
     */
    @Transactional
    public SaveOrderDto saveOrder(SaveOrderDto saveOrderDto, SessionUserDto sessionUserDto, SaveDeliveryDto deliveryDto,
                                  List<Long> productIds, List<SaveOrderProductDto> saveOrderProductDtos, Long couponId) {

        Order order = orderRepository.save(saveOrderDto.toEntity());
        User user = userService.findById(sessionUserDto.getId());
        Delivery delivery = deliveryService.findById(deliveryDto.getId());
        Coupon coupon = couponService.findById(couponId);
        List<OrderProduct> orderProducts = getOrderProducts(sessionUserDto, productIds, saveOrderProductDtos, coupon);
        coupon.updateCouponStatus(CouponStatus.USED);
        order.createOrder(user, delivery, orderProducts);
        return SaveOrderDto.of(order);

    }

    private List<OrderProduct> getOrderProducts(SessionUserDto sessionUserDto, List<Long> productIds, List<SaveOrderProductDto> saveOrderProductDtos, Coupon coupon) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            SaveOrderProductDto orderProductDto = saveOrderProductDtos.get(i);
            SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(sessionUserDto, orderProductDto, productId, coupon);
            OrderProduct orderProduct = orderProductService.findById(saveOrderProductDto.getId());
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 주문은 존재하지 않습니다.");
        });
    }
}

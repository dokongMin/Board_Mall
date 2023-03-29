package com.dokong.board.web.service;

import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.OrderRepository;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final DeliveryService deliveryService;
    private final OrderProductService orderProductService;

    @Transactional
    public SaveOrderDto saveOrder(SaveOrderDto saveOrderDto) {
        User user = userService.findById(saveOrderDto.getUserId());
        Delivery delivery = deliveryService.findById(saveOrderDto.getSaveDeliveryDto().getId());
        List<OrderProduct> orderProducts = getOrderProducts(saveOrderDto.getSaveOrderProductDtos());
        Order order = orderRepository.save(saveOrderDto.toEntity());
        order.createOrder(user, delivery, orderProducts);
        return SaveOrderDto.of(order);
    }


    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findById(orderId);
        order.cancelOrder();
    }

    private List<OrderProduct> getOrderProducts(List<SaveOrderProductDto> saveOrderProductDtos) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < saveOrderProductDtos.size(); i++) {
            SaveOrderProductDto orderProductDto = saveOrderProductDtos.get(i);
            SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(orderProductDto);
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

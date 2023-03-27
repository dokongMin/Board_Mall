package com.dokong.board.web.service;


import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.CouponMinPriceException;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;

    private final UserService userService;
    @Transactional
    public SaveOrderProductDto saveOrderProduct(SessionUserDto sessionUserDto, SaveOrderProductDto saveOrderProductDto, Long productId) {
        discountByUserRole(sessionUserDto, saveOrderProductDto);
        OrderProduct orderProduct = orderProductRepository.save(saveOrderProductDto.toEntity());
        Product product = productService.findById(productId);
        orderProduct.order(product);
        return SaveOrderProductDto.of(orderProduct);
    }



    @Transactional
    public SaveOrderProductDto saveOrderProduct(SessionUserDto sessionUserDto, SaveOrderProductDto saveOrderProductDto, Long productId, Coupon coupon) {
        discountByUserRole(sessionUserDto, saveOrderProductDto);
        if (coupon.getCouponStatus() == CouponStatus.UNUSED) {
            discountByCoupon(saveOrderProductDto, coupon);
        }
        OrderProduct orderProduct = orderProductRepository.save(saveOrderProductDto.toEntity());
        Product product = productService.findById(productId);
        orderProduct.order(product);
        return SaveOrderProductDto.of(orderProduct);
    }

    private void discountByCoupon(SaveOrderProductDto saveOrderProductDto, Coupon coupon) {
        if (saveOrderProductDto.getOrderItemPrice() < 10000) {
            throw new CouponMinPriceException("쿠폰을 사용하기 위해서는 최소 10,000 원 이상 구매해야 합니다.");
        }
        int currentDiscount = saveOrderProductDto.getOrderItemPrice() / coupon.getCouponRate();
        int currentPrice = saveOrderProductDto.getOrderItemPrice() - currentDiscount;
        saveOrderProductDto.setOrderItemPrice(currentPrice);
    }


    private void discountByUserRole(SessionUserDto sessionUserDto, SaveOrderProductDto saveOrderProductDto) {
        User user = userService.findById(sessionUserDto.getId());
        int discountItemPrice = user.discountByUserRole(saveOrderProductDto.getOrderItemCount(), saveOrderProductDto.getOrderItemPrice());
        saveOrderProductDto.setOrderItemPrice(discountItemPrice);
    }

    @Transactional
    public void cancelOrderProduct(Long orderProductId) {
        OrderProduct orderProduct = findById(orderProductId);
        orderProduct.cancel();
        orderProductRepository.delete(orderProduct);
    }

    public OrderProduct findById(Long id) {
       return orderProductRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 주문 상품은 존재하지 않습니다.");
        });
    }
}

package com.dokong.board.web.service;


import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.web.dto.SaveOrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;

    @Transactional
    public SaveOrderProductDto saveOrderProduct(SaveOrderProductDto saveOrderProductDto, Long productId) {
        OrderProduct orderProduct = orderProductRepository.save(saveOrderProductDto.toEntity());
        Product product = productService.findById(productId);
        orderProduct.order(product);
        return SaveOrderProductDto.of(orderProduct);
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

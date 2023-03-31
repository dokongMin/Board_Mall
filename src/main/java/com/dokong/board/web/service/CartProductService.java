package com.dokong.board.web.service;


import com.dokong.board.domain.CartProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.CartProductRepository;
import com.dokong.board.web.dto.savecartproductdto.DeleteCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductRespDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductService {

    private final CartProductRepository cartProductRepository;

    private final ProductService productService;

    private final UserService userService;

    @Transactional
    public SaveCartProductRespDto saveCartProduct(SaveCartProductDto saveCartProductDto) {
        User user = userService.findById(saveCartProductDto.getUserId());
        Product product = productService.findById(saveCartProductDto.getProductId());
        CartProduct cartProduct = cartProductRepository.save(saveCartProductDto.toEntity());
        cartProduct.createCartOrder(user, product);
        return SaveCartProductRespDto.of(cartProduct, user, product);
    }

    @Transactional
    public DeleteCartProductDto deleteCartProduct(DeleteCartProductDto deleteCartProductDto) {
        List<CartProduct> collect = deleteCartProductDto.getCartProductDto().stream()
                .map(c -> findById(c.getId()))
                .collect(Collectors.toList());

        collect.stream()
                .forEach(c -> cartProductRepository.delete(c));

        return deleteCartProductDto;
    }

    public CartProduct findById(Long id) {
        return cartProductRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 장바구니 상품은 존재하지 않습니다.");
        });
    }
}

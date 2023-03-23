package com.dokong.board.web.service;


import com.dokong.board.domain.CartProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.CartProductRepository;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductRespDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductService {

    private final CartProductRepository cartProductRepository;

    private final ProductService productService;

    private final UserService userService;

    @Transactional
    public SaveCartProductRespDto saveCartProduct(SaveCartProductDto saveCartProductDto, SessionUserDto sessionUserDto, Long productId) {
        User user = userService.findById(sessionUserDto.getId());
        Product product = productService.findById(productId);
        CartProduct cartProduct = cartProductRepository.save(saveCartProductDto.toEntity());
        cartProduct.createCartOrder(user, product);
        return SaveCartProductRespDto.of(cartProduct, user, product);
    }

    public CartProduct findById(Long id) {
        return cartProductRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 장바구니 상품은 존재하지 않습니다.");
        });
    }
}

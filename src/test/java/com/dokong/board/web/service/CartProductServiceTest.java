package com.dokong.board.web.service;

import com.dokong.board.domain.CartProduct;
import com.dokong.board.domain.Product;
import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductRespDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CartProductServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;
    @Autowired
    ProductService productService;
    @Autowired
    CartProductService cartProductService;
    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("장바구니_저장")
    public void saveCartProduct () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        CategoryDto categoryDto = getCategoryDto();
        categoryService.saveCategory(categoryDto);

        SaveProductDto saveProductDto = getSaveProductDtoApple();
        SaveProductDto savedProduct = productService.saveProduct(saveProductDto, categoryDto.getCategoryName());
        Product product = productService.findById(savedProduct.getId());

        SaveCartProductDto saveCartProductDto = getSaveCartProductDto();
        SaveCartProductRespDto saveCartProductRespDto = cartProductService.saveCartProduct(saveCartProductDto, sessionUserDto, product.getId());


        User user = userService.findById(sessionUserDto.getId());
        CartProduct cartProduct = cartProductService.findById(saveCartProductRespDto.getCartId());
        // then
        assertThat(user.getCartProducts().get(0).getCartItemPrice()).isEqualTo(2000);
        assertThat(user.getCartProducts().get(0).getCartItemCount()).isEqualTo(10);
        assertThat(cartProduct.getProduct().getItemName()).isEqualTo("사과");
        assertThat(product.getCartProducts().get(0).getCartItemPrice()).isEqualTo(2000);
        assertThat(user.getCartProducts().get(0).getProduct().getItemName()).isEqualTo("사과");
     }

    private SaveCartProductDto getSaveCartProductDto() {
        return SaveCartProductDto.builder()
                .cartItemCount(10)
                .cartItemPrice(2000)
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

}
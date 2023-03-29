package com.dokong.board.web.service;

import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import com.dokong.board.repository.OrderProductRepository;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class OrderProductServiceTest {


    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    @Test
    @DisplayName("브론즈_회원_주문_상품_저장")
    public void saveOrderProductByBronzeRole () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        CategoryDto getCategoryDto = getCategoryDto();
        CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

        SaveProductDto getSaveProductDto = getSaveProductDto();
        SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto);
        Product product = productService.findById(saveProductDto.getId());

        SaveOrderProductDto saveOrderProductDto1 = getOrderProductDto1(sessionUserDto, product);
        SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(saveOrderProductDto1);

        // then
        assertThat(saveOrderProductDto.getOrderItemPrice()).isEqualTo(90000);
        assertThat(saveOrderProductDto.getOrderItemCount()).isEqualTo(10);
        assertThat(product.getItemStock()).isEqualTo(90);
     }



    @Test
    @DisplayName("실버_회원_주문_상품_저장")
    public void saveOrderProductBySliverRole () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        User user = userService.findById(sessionUserDto.getId());
        user.updateUserRole(UserRole.SILVER);

        CategoryDto getCategoryDto = getCategoryDto();
        CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

        SaveProductDto getSaveProductDto = getSaveProductDto();
        SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto);
        Product product = productService.findById(saveProductDto.getId());

        SaveOrderProductDto saveOrderProductDto1 = getOrderProductDto1(sessionUserDto, product);
        SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(saveOrderProductDto1);

        // then
        assertThat(saveOrderProductDto.getOrderItemCount()).isEqualTo(10);
        assertThat(product.getItemStock()).isEqualTo(90);
    }
    @Test
    @DisplayName("골드_회원_주문_상품_저장")
    public void saveOrderProductByGoldRole () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        User user = userService.findById(sessionUserDto.getId());
        user.updateUserRole(UserRole.GOLD);

        CategoryDto getCategoryDto = getCategoryDto();
        CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

        SaveProductDto getSaveProductDto = getSaveProductDto();
        SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto);
        Product product = productService.findById(saveProductDto.getId());

        SaveOrderProductDto saveOrderProductDto1 = getOrderProductDto1(sessionUserDto, product);
        SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(saveOrderProductDto1);

        // then
        assertThat(saveOrderProductDto.getOrderItemCount()).isEqualTo(10);
        assertThat(product.getItemStock()).isEqualTo(90);
    }


     @Test
     @DisplayName("주문_상품_취소")
     public void cancelOrderProduct () throws Exception{
         JoinUserDto joinUserDto = getJoinUserDto();
         userService.saveUser(joinUserDto);

         LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
         SessionUserDto sessionUserDto = loginService.login(loginUserDto);

         CategoryDto getCategoryDto = getCategoryDto();
         CategoryDto categoryDto = categoryService.saveCategory(getCategoryDto);

         SaveProductDto getSaveProductDto = getSaveProductDto();
         SaveProductDto saveProductDto = productService.saveProduct(getSaveProductDto);
         Product product = productService.findById(saveProductDto.getId());

         SaveOrderProductDto saveOrderProductDto1 = getOrderProductDto1(sessionUserDto, product);
         SaveOrderProductDto saveOrderProductDto = orderProductService.saveOrderProduct(saveOrderProductDto1);
         OrderProduct orderProduct = orderProductService.findById(saveOrderProductDto.getId());
         //when
         orderProductService.cancelOrderProduct(orderProduct.getId());
         // then
         assertThat(product.getItemStock()).isEqualTo(100);
         assertThat(product.getOrderProducts().size()).isEqualTo(0);
         assertThat(orderProductRepository.findAll().size()).isEqualTo(0);
      }


    private SaveOrderProductDto getOrderProductDto1(SessionUserDto sessionUserDto, Product product) {
        return SaveOrderProductDto.builder()
                .orderItemPrice(product.getItemPrice())
                .orderItemCount(10)
                .userId(sessionUserDto.getId())
                .productId(product.getId())
                .build();
    }
    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .categoryName("과일")
                .build();
    }

    private SaveProductDto getSaveProductDto() {
        return SaveProductDto.builder()
                .itemName("사과")
                .itemPrice(10000)
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
}
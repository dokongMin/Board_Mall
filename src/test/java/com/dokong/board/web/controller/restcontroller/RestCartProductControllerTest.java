package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.dto.savecartproductdto.DeleteCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductRespDto;
import com.dokong.board.web.service.CartProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dokong.board.web.dto.savecartproductdto.DeleteCartProductDto.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestCartProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartProductService cartProductService;



    @DisplayName("장바구니에 상품을 저장한다.")
    @Test
    void saveCartProduct () throws Exception{
        // given
        SaveCartProductDto request = getSaveCartProductDto();
        SaveCartProductRespDto response = SaveCartProductRespDto.builder()
                .cartItemCount(20)
                .cartItemPrice(1000)
                .build();
        when(cartProductService.saveCartProduct(request)).thenReturn(response);
        // when

        mockMvc.perform(post("/items/add-cart")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
     }


     @DisplayName("장바구니에서 상품을 삭제한다.")
     @Test
     void deleteCartProduct () throws Exception{
         // given
         CartProductDto items = getCartProductDto();
         List<CartProductDto> dtos = List.of(items);
         DeleteCartProductDto request = builder()
                 .cartProductDto(dtos)
                 .build();


         when(cartProductService.deleteCartProduct(request)).thenReturn(request);
         // when
         mockMvc.perform(post("/items/delete-cart")
                         .content(objectMapper.writeValueAsString(request))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.code").value("CREATED"))
                 .andExpect(jsonPath("$.msg").value("Delete Request Success"));
      }


      @DisplayName("유저 아이디에 있는 전체 장바구니 품목을 조회한다.")
      @Test
      void findAllCartProductByUserId () throws Exception{
          // given
          Long id = 1L;
          SaveCartProductRespDto dto = SaveCartProductRespDto.builder()
                  .cartId(id)
                  .cartItemPrice(2000)
                  .build();
          List<SaveCartProductRespDto> request = List.of(dto);
          when(cartProductService.findAllByUserId(id)).thenReturn(request);

          // when
          mockMvc.perform(get("/items/cart/find-all/" + id))
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.code").value("OK"))
                  .andExpect(jsonPath("$.msg").value("Request Success"));
       }

    private CartProductDto getCartProductDto() {
        return CartProductDto.builder()
                .itemName("aaa")
                .build();
    }

    private SaveCartProductDto getSaveCartProductDto() {
        return SaveCartProductDto.builder()
                .productId(1L)
                .userId(1L)
                .cartItemPrice(10)
                .cartItemCount(20)
                .build();
    }
}
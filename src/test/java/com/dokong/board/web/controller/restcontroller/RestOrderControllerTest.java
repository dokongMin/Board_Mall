package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderdto.SaveOrderRespDto;
import com.dokong.board.web.service.OrderService;
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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;


    @DisplayName("주문을 생성한다.")
    @Test
    void createOrder () throws Exception{
        // given
        SaveOrderDto request = getSaveOrderDto();
        SaveOrderRespDto response = getSaveOrderRespDto();
        when(orderService.saveOrder(request)).thenReturn(response);
        // when
        mockMvc.perform(post("/order/add")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
     }


     @DisplayName("주문을 취소한다.")
     @Test
     void cancelOrder () throws Exception{
         // given
         Long id = 1L;
         when(orderService.cancelOrder(1L)).thenReturn(id);

         // when
         mockMvc.perform(post("/order/cancel/" + id))
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.code").value("CREATED"))
                 .andExpect(jsonPath("$.msg").value("Delete Request Success"));

      }

    private SaveOrderRespDto getSaveOrderRespDto() {
        return SaveOrderRespDto.builder()
                .id(1L)
                .username("aaa")
                .build();
    }

    private SaveOrderDto getSaveOrderDto() {
        return SaveOrderDto.builder()
                .userId(1L)
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .build();
    }
}
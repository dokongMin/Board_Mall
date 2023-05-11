package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.product.ProductStatus;
import com.dokong.board.repository.product.ProductRepository;
import com.dokong.board.web.dto.product.DeleteProductRespDto;
import com.dokong.board.web.dto.product.FindProductDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.product.UpdateProductDto;
import com.dokong.board.web.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void saveProduct() throws Exception {
        // given
        SaveProductDto request = getSaveProduct();

        when(productService.saveProduct(request)).thenReturn(request);
        // when
        mockMvc.perform(post("/items/add")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("상품 정보를 수정한다.")
    @Test
    void updateProduct() throws Exception {
        // given
        UpdateProductDto request = getUpdateProductDto();
        Long id = 1L;
        when(productService.updateProduct(id, request)).thenReturn(request);
        // when
        mockMvc.perform(post("/items/update/" + id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @DisplayName("상품 전체를 조회한다.")
    @Test
    void findAllProduct() throws Exception {
        // given
        FindProductDto request1 = getProduct();
        FindProductDto request2 = getProduct();
        List<FindProductDto> productRequest = List.of(request1, request2);

        when(productService.findAll()).thenReturn(productRequest);
        // when
        mockMvc.perform(get("/items/item-list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.msg").value("Request Success"));
    }


    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProduct () throws Exception{
        // given
        Long id = 1L;
        DeleteProductRespDto request = getDeleteProductRespDto(id);
        when(productService.deleteProduct(id)).thenReturn(request);

        // when
        mockMvc.perform(put("/items/delete/" + id))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.msg").value("Update Request Success"));
     }

    private DeleteProductRespDto getDeleteProductRespDto(Long id) {
        return DeleteProductRespDto.builder()
                .id(id)
                .productStatus(ProductStatus.DELETE.getDescription())
                .build();
    }

    private FindProductDto getProduct() {
        return FindProductDto.builder()
                .itemName("딸기")
                .itemPrice(2000)
                .itemStock(300)
                .categoryName("과일")
                .productStatus(ProductStatus.OPEN)
                .build();
    }

    private UpdateProductDto getUpdateProductDto() {
        return UpdateProductDto.builder()
                .itemName("포도")
                .itemPrice(2300)
                .itemStock(30)
                .build();
    }


    private SaveProductDto getSaveProduct() {
        return SaveProductDto.builder()
                .itemName("사과")
                .itemPrice(2000)
                .itemStock(100)
                .productStatus(ProductStatus.OPEN)
                .categoryName("과일")
                .build();
    }
}
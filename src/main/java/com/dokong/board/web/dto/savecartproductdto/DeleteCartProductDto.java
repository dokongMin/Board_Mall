package com.dokong.board.web.dto.savecartproductdto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteCartProductDto {

    private List<CartProductDto> cartProductDto;


    @Getter
    public static class CartProductDto {
        private Long id;
        private String itemName;
    }

}

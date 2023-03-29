package com.dokong.board.web.dto.orderproductdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveOrderProductCouponDto {

    private Long id;
    private int orderItemPrice;
    private int orderItemCount;

    private Long userId;
    private Long productId;
    private Long couponId;
}

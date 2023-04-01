package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderStatusDto {

    private OrderStatus orderStatus;
}

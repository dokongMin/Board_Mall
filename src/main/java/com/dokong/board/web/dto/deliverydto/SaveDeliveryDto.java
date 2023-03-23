package com.dokong.board.web.dto.deliverydto;


import com.dokong.board.domain.Address;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveDeliveryDto {

    private Long id;
    private Address address;
    private DeliveryStatus deliveryStatus;

    @Builder
    public SaveDeliveryDto(Long id, Address address, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }

    public Delivery toEntity() {
        return Delivery.builder()
                .address(address)
                .deliveryStatus(DeliveryStatus.DELIVER_READY)
                .build();
    }

    public static SaveDeliveryDto of(Delivery delivery) {
        return SaveDeliveryDto.builder()
                .id(delivery.getId())
                .deliveryStatus(delivery.getDeliveryStatus())
                .address(delivery.getAddress())
                .build();
    }
}

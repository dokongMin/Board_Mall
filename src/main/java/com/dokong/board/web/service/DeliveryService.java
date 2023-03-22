package com.dokong.board.web.service;

import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.repository.DeliveryRepository;
import com.dokong.board.web.dto.SaveDeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public SaveDeliveryDto saveDelivery(SaveDeliveryDto saveDeliveryDto) {
        return SaveDeliveryDto.of(deliveryRepository.save(saveDeliveryDto.toEntity()));
    }

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 배송지는 존재하지 않습니다.");
        });
    }
}

package com.inditex.demo.price.infrastructure.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;

public class PriceMapper {

    private static PriceMapper instance;

    private PriceMapper() {
    }

    public static PriceMapper getInstance() {
        if (instance == null) {
            instance = new PriceMapper();
        }
        return instance;
    }


    public Price toDTO(PriceEntity priceEntity) {
        if (priceEntity == null) {
            return null;
        }
        return Price.builder()
                    .productId(priceEntity.getProductId())
                    .brandId(priceEntity.getBrandId())
                    .startDate(priceEntity.getStartDate())
                    .endDate(priceEntity.getEndDate())
                    .priceList(priceEntity.getPriceList())
                    .price(priceEntity.getPrice())
                    .currency(priceEntity.getCurrency())
                    .priority(priceEntity.getPriority())
                    .build();
    }

    public List<Price> toDTO(List<PriceEntity> priceEntity) {
        if (priceEntity == null) {
            return new ArrayList<>();
        }
        return priceEntity.stream().map(this::toDTO).toList();
    }
}

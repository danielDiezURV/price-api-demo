package com.inditex.demo.price.infrastructure.persistence.mapper;

import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;

public class PriceMapper {

    private static PriceMapper instance;

    public static Price toPrice(PriceEntity get) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private PriceMapper() {
    }

    public static PriceMapper getInstance() {
        if (instance == null) {
            instance = new PriceMapper();
        }
        return instance;
    }


    public Price toDTO(PriceEntity priceEntity) {
        return Price.builder()
                    .productId(priceEntity.getProductId())
                    .brandId(priceEntity.getBrandId())
                    .startDate(priceEntity.getStartDate())
                    .endDate(priceEntity.getEndDate())
                    .priceList(priceEntity.getPriceList())
                    .price(priceEntity.getPrice())
                    .currency(priceEntity.getCurrency())
                    .build();
    }
}
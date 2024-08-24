package com.inditex.demo.price.domain.repository;

import java.time.LocalDateTime;

import com.inditex.demo.price.domain.dto.Price;

public interface PriceRepository {
    Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

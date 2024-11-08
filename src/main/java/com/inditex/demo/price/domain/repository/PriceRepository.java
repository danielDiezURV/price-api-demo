package com.inditex.demo.price.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.inditex.demo.price.domain.dto.Price;

public interface PriceRepository {
    List<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

package com.inditex.demo.price.application.find;

import java.time.LocalDateTime;

import com.inditex.demo.price.domain.dto.Price;

public interface FindApplicablePrice {

    /** 
    * Find the applicable price for a product and brand at a given date
    * @param applicationDate
    * @param productId
    * @param brandId 
    * @return Price
    **/
    Price get(LocalDateTime applicationDate, Long productId, Long brandId);
}

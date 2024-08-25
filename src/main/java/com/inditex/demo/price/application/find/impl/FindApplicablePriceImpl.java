package com.inditex.demo.price.application.find.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.domain.repository.PriceRepository;
import com.inditex.demo.shared.application.ApiError;
import com.inditex.demo.shared.application.ApiResponse;
import com.inditex.demo.shared.application.ResponseEntityHandler;

@Service
public class FindApplicablePriceImpl implements FindApplicablePrice {

    private final PriceRepository priceRepository;

    @Autowired
    public FindApplicablePriceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<Price>> get(LocalDateTime applicationDate, Long productId, Long brandId){
        Price price = priceRepository.findApplicablePrice(applicationDate, productId, brandId);
        if (price == null) {
            ApiError error = ApiError.builder()
                                     .title("Price not found")
                                     .detail("Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate)
                                     .url("http://localhost:8080/prices/")
                                     .build();
            return ResponseEntityHandler.generateError(error, HttpStatus.NOT_FOUND);
        }
        ApiResponse<Price> response = new ApiResponse<>(price, "Price found for product " + productId + " and brand " + brandId + " at date " + applicationDate, null);
        return ResponseEntityHandler.generate(response, HttpStatus.OK);
    }

}

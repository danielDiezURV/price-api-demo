package com.inditex.demo.price.application.find;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceNotFoundException;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceParamException;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.shared.infrastructure.ApiResponse;


public interface FindApplicablePrice {

    /** 
    * Find the applicable price for a product and brand at a given date
    * @param applicationDate
    * @param productId
    * @param brandId 
    * @return Price
     * @throws ExceptionControllerPriceException 
    * @throws ExceptionControllerPriceNotFoundException
    **/
    ResponseEntity<ApiResponse<Price>> get(LocalDateTime applicationDate, Long productId, Long brandId) throws ExceptionControllerPriceParamException, ExceptionControllerPriceNotFoundException;
}

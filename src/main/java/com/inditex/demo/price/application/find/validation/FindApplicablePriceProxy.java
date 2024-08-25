package com.inditex.demo.price.application.find.validation;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceNotFoundException;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceParamException;
import com.inditex.demo.price.application.find.impl.FindApplicablePriceImpl;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.shared.application.ApiResponse;

@Service
public class FindApplicablePriceProxy implements FindApplicablePrice {
    
    private final FindApplicablePriceImpl findApplicablePrice;
    
    @Autowired
    public FindApplicablePriceProxy(FindApplicablePriceImpl findApplicablePrice) {
        this.findApplicablePrice = findApplicablePrice;
    }


    @Override
    public ResponseEntity<ApiResponse<Price>> get(LocalDateTime applicationDate, Long productId, Long brandId) throws ExceptionControllerPriceParamException, ExceptionControllerPriceNotFoundException {
        if (applicationDate == null) {
            throw new ExceptionControllerPriceParamException("applicationDate is required");
        }
        if (productId == null) {
            throw new ExceptionControllerPriceParamException("productId is required");
        }
        if (brandId == null) {
            throw new ExceptionControllerPriceParamException("brandId is required");
        }

        ResponseEntity<ApiResponse<Price>> response =  findApplicablePrice.get(applicationDate, productId, brandId);
        if (Optional.ofNullable(response.getBody()).map(body -> body.getData()).isEmpty()) {
            throw new ExceptionControllerPriceNotFoundException("Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate);
        }
        return response;
    }

}

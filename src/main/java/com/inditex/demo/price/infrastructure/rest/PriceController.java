package com.inditex.demo.price.infrastructure.rest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.demo.price.application.find.exceptions.*;
import com.inditex.demo.price.application.find.validation.FindApplicablePriceProxy;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.shared.application.ApiResponse;

@RestController
@RequestMapping("/prices")
public class PriceController {
    
    @Autowired
    private FindApplicablePriceProxy findApplicablePriceProxy;

    @GetMapping
    public ResponseEntity<ApiResponse<Price>> getPrice(@RequestParam(value = "applicationDate", required = true) LocalDateTime applicationDate,
                                                       @RequestParam(value = "productId", required = true)  Long productId, 
                                                       @RequestParam(value = "brandId", required = true) Long brandId) throws ExceptionControllerPriceParamException, ExceptionControllerPriceNotFoundException {
        return findApplicablePriceProxy.get(applicationDate, productId, brandId);
    }
}

package com.inditex.demo.price.infrastructure.rest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.shared.infrastructure.ApiResponse;
import com.inditex.demo.shared.infrastructure.ResponseEntityHandler;

@RestController
@RequestMapping("/prices")
public class PriceController {
    
    @Autowired
    private FindApplicablePrice findApplicablePrice;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Price>> getPrice(@RequestParam(value = "applicationDate", required = true) 
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                                       @RequestParam(value = "productId", required = true)  Long productId, 
                                                       @RequestParam(value = "brandId", required = true) Long brandId) {
        
        Price price = findApplicablePrice.get(applicationDate, productId, brandId);
        ApiResponse<Price> response = new ApiResponse<>(price, "Price found for product " + productId + " and brand " + brandId + " at date " + applicationDate, null);
        return ResponseEntityHandler.generate(response, HttpStatus.OK);
    }
}

package com.inditex.demo.price.application.find.validation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.application.find.exceptions.PriceNotFoundException;
import com.inditex.demo.price.application.find.impl.FindApplicablePriceImpl;
import com.inditex.demo.price.domain.dto.Price;

@Service
public class FindApplicablePriceProxy implements FindApplicablePrice {
    
    private final FindApplicablePriceImpl findApplicablePrice;
    
    @Autowired
    public FindApplicablePriceProxy(FindApplicablePriceImpl findApplicablePrice) {
        this.findApplicablePrice = findApplicablePrice;
    }


    @Override
    public Price get(LocalDateTime applicationDate, Long productId, Long brandId) throws PriceNotFoundException {
        Price price =  findApplicablePrice.get(applicationDate, productId, brandId);
        if (price == null) {
            throw new PriceNotFoundException("Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate);
        }
        return price;
    }

}

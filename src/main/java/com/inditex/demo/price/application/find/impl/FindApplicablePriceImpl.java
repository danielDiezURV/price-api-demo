package com.inditex.demo.price.application.find.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.domain.repository.PriceRepository;

@Service
public class FindApplicablePriceImpl implements FindApplicablePrice {

    private final PriceRepository priceRepository;

    @Autowired
    public FindApplicablePriceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price get(LocalDateTime applicationDate, Long productId, Long brandId){
        List<Price> priceList = priceRepository.findApplicablePrice(applicationDate, productId, brandId);
        if (CollectionUtils.isEmpty(priceList)) {
            return null;
        }
        return priceList.stream().max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority())).orElse(null);
    }

}

package com.inditex.demo.price.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.domain.repository.PriceRepository;
import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.demo.price.infrastructure.persistence.mapper.PriceMapper;
import com.inditex.demo.price.infrastructure.persistence.repository.jpa.PriceJpaRepository;

@Service
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    
    @Autowired
    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public List<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<PriceEntity> entityPrice = this.priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId);
        return PriceMapper.getInstance().toDTO(entityPrice);
    }

}

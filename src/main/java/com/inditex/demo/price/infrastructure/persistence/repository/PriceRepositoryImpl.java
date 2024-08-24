package com.inditex.demo.price.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        Optional<PriceEntity> entityPrices = this.priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId);
        return entityPrices.isPresent() ? PriceMapper.getInstance().toDTO(entityPrices.get()) : null;
    }

}

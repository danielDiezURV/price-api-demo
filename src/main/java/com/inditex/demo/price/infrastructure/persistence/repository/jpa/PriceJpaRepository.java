package com.inditex.demo.price.infrastructure.persistence.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    @Query(value = "SELECT p " +
            "FROM PriceEntity p " +
            "WHERE p.productId = :productId " +
            "AND p.brandId = :brandId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findApplicablePrice(@Param("applicationDate") LocalDateTime applicationDate, @Param("productId") Long productId, @Param("brandId") Long brandId);
}

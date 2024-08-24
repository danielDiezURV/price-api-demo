package com.inditex.demo.price.infrastructure.persistence.repository.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
        @Query(value = "SELECT * " +
                "FROM prices p " +
                "WHERE  p.product_id = :productId " +
                "       AND p.brand_id = :brandId " +
                "       AND :applicationDate BETWEEN p.start_date AND p.end_date" +
                "       AND p.endDate " +
                "ORDER BY p.priority DESC " +
                "LIMIT 1", nativeQuery = true)
        Optional<PriceEntity> findApplicablePrice(@Param("applicationDate") LocalDateTime applicationDate, @Param("productId") Long productId, @Param("brandId") Long brandId);
}

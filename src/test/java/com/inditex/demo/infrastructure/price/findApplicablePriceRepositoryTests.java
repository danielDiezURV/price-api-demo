package com.inditex.demo.infrastructure.price;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.inditex.demo.PriceApiDemoApplicationTests;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.demo.price.infrastructure.persistence.repository.PriceRepositoryImpl;
import com.inditex.demo.price.infrastructure.persistence.repository.jpa.PriceJpaRepository;

public class findApplicablePriceRepositoryTests extends PriceApiDemoApplicationTests {
    
        private PriceJpaRepository priceJpaRepository;
        private PriceRepositoryImpl priceRepositoryimpl;

        private PriceEntity mockPrice() {
                return PriceEntity.builder()
                        .id(1L)
                        .brandId(1L)
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(35.50)
                        .currency("EUR")
                        .build();
        }

        @BeforeEach
        private void setUp() {
                this.priceJpaRepository = mock(PriceJpaRepository.class);
                this.priceRepositoryimpl = new PriceRepositoryImpl(priceJpaRepository);
        }
        
        @Test
        public void findApplicablePriceOK() {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                List<PriceEntity> mockPriceResponse = Arrays.asList(mockPrice());
                // Mock
                when(this.priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(mockPriceResponse);
                // When
                Price price = priceRepositoryimpl.findApplicablePrice(applicationDate, productId, brandId);
                // Then
                assertEquals(price.getBrandId(), mockPrice().getBrandId());
                assertEquals(price.getStartDate(), mockPrice().getStartDate());
                assertEquals(price.getEndDate(), mockPrice().getEndDate());
                assertEquals(price.getPriceList(), mockPrice().getPriceList());
                assertEquals(price.getProductId(), mockPrice().getProductId());
                assertEquals(price.getPrice(), mockPrice().getPrice());
                assertEquals(price.getCurrency(), mockPrice().getCurrency());

                assertEquals(price.getProductId(), productId);
                assertEquals(price.getBrandId(), brandId);
                assertTrue(applicationDate.isAfter(price.getStartDate()) || applicationDate.isEqual(price.getStartDate()));
                assertTrue(applicationDate.isBefore(price.getEndDate()) || applicationDate.isEqual(price.getEndDate()));
        }

        @Test
        public void findApplicablePriceNotFound() {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 1, 14, 10, 0, 0);
                // Mock
                when(this.priceJpaRepository.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(null);
                // When
                Price price = priceRepositoryimpl.findApplicablePrice(applicationDate, productId, brandId);
                // Then
                assertEquals(price, null);
        }
}

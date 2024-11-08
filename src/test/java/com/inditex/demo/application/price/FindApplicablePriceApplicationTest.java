package com.inditex.demo.application.price;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.demo.PriceApiDemoApplicationTests;
import com.inditex.demo.price.application.find.exceptions.PriceNotFoundException;
import com.inditex.demo.price.application.find.impl.FindApplicablePriceImpl;
import com.inditex.demo.price.application.find.validation.FindApplicablePriceProxy;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.domain.repository.PriceRepository;

public class FindApplicablePriceApplicationTest extends PriceApiDemoApplicationTests {
    
        private PriceRepository priceRepository;
        private FindApplicablePriceImpl findApplicablePriceImpl;
        private FindApplicablePriceProxy findApplicablePriceProxy;

        private Price mockPrice(Integer priority) {
                return Price.builder()
                        .brandId(1L)
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(priority)
                        .price(35.50)
                        .currency("EUR")
                        .build();
        }

        @BeforeEach
        private void setUp() {
                this.priceRepository = mock(PriceRepository.class);
                this.findApplicablePriceImpl = new FindApplicablePriceImpl(this.priceRepository);
                this.findApplicablePriceProxy = new FindApplicablePriceProxy(this.findApplicablePriceImpl);              
        }
        

        @DisplayName("Test Application findApplicablePrice OK")     
        @Test
        public void findApplicablePriceOK() {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                Price mockPrice = mockPrice(1);
                List<Price> mockPriceResponse = Collections.singletonList(mockPrice);
                // Mock
                when(this.priceRepository.findApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(mockPriceResponse);
                // When
                Price response = findApplicablePriceProxy.get(applicationDate, productId, brandId);
                // Then
                verify(this.priceRepository).findApplicablePrice(applicationDate, productId, brandId);
                assertEquals(productId, response.getProductId());
                assertEquals(brandId, response.getBrandId());
                assertTrue(applicationDate.isAfter(response.getStartDate()) || applicationDate.isEqual(response.getStartDate()));
                assertTrue(applicationDate.isBefore(response.getEndDate()) || applicationDate.isEqual(response.getEndDate()));
        }

        @DisplayName("Test Application findApplicablePrice with multiple results by Priority OK")     
        @Test
        public void findApplicablePricePriorityOK() {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                Price mockPricePriority2 = mockPrice(2);
                Price mockPricePriority1 = mockPrice(1);
                Price mockPricePriority0 = mockPrice(0);
                List<Price> mockPriceResponse = List.of(mockPricePriority2 ,mockPricePriority1, mockPricePriority0);
                // Mock
                when(this.priceRepository.findApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(mockPriceResponse);
                // When
                Price response = findApplicablePriceProxy.get(applicationDate, productId, brandId);
                // Then
                verify(this.priceRepository).findApplicablePrice(applicationDate, productId, brandId);
                assertEquals(productId, response.getProductId());
                assertEquals(brandId, response.getBrandId());
                assertTrue(applicationDate.isAfter(response.getStartDate()) || applicationDate.isEqual(response.getStartDate()));
                assertTrue(applicationDate.isBefore(response.getEndDate()) || applicationDate.isEqual(response.getEndDate()));
                assertEquals(2, response.getPriority());
        }
 
        @DisplayName("Test Application findApplicablePrice throw PriceNotFoundException when price not found")
        @Test
        public void givenNotFound_whenFindApplicablePrice_thenThrowPriceNotFoundException() throws PriceNotFoundException {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                // Mock
                when(this.priceRepository.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(null);
                // When
                PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> {findApplicablePriceProxy.get(applicationDate, productId, brandId);});
                // Then
                verify(this.priceRepository).findApplicablePrice(applicationDate, productId, brandId);
                assertEquals(exception.getMessage(), "Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate);
        }

}

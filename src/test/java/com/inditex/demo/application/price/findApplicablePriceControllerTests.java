package com.inditex.demo.application.price;

import java.time.LocalDateTime;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.inditex.demo.PriceApiDemoApplicationTests;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceNotFoundException;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceParamException;
import com.inditex.demo.price.application.find.impl.FindApplicablePriceImpl;
import com.inditex.demo.price.application.find.validation.FindApplicablePriceProxy;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.price.domain.repository.PriceRepository;
import com.inditex.demo.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.demo.price.infrastructure.persistence.mapper.PriceMapper;
import com.inditex.demo.shared.infrastructure.ApiResponse;

public class findApplicablePriceControllerTests extends PriceApiDemoApplicationTests {
    
        private PriceRepository priceRepository;
        private FindApplicablePriceImpl findApplicablePriceImpl;
        private FindApplicablePriceProxy findApplicablePriceProxy;

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
                this.priceRepository = mock(PriceRepository.class);
                this.findApplicablePriceImpl = new FindApplicablePriceImpl(this.priceRepository);
                this.findApplicablePriceProxy = new FindApplicablePriceProxy(this.findApplicablePriceImpl);                
        }
        

        @DisplayName("Test findApplicablePrice OK")     
        @Test
        public void findApplicablePriceOK() throws ExceptionControllerPriceParamException, ExceptionControllerPriceNotFoundException {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                Price mockPrice = PriceMapper.getInstance().toDTO(mockPrice());
                // Mock
                when(this.priceRepository.findApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(mockPrice);
                // When
                ResponseEntity<ApiResponse<Price>> response = findApplicablePriceProxy.get(applicationDate, productId, brandId);
                // Then
                verify(this.priceRepository).findApplicablePrice(applicationDate, productId, brandId);
                assertEquals(response.getStatusCode(), HttpStatus.OK);

                Price price = response.getBody().getData();
                assertEquals(productId, price.getProductId());
                assertEquals(brandId, price.getBrandId());
                assertTrue(applicationDate.isAfter(price.getStartDate()) || applicationDate.isEqual(price.getStartDate()));
                assertTrue(applicationDate.isBefore(price.getEndDate()) || applicationDate.isEqual(price.getEndDate()));
        }


        @DisplayName("Test throw ExceptionControllerPriceNotFoundException when price not found")
        @Test
        public void givenNotFound_whenFindApplicablePrice_thenThrowExceptionControllerPriceNotFoundException() throws ExceptionControllerPriceParamException, ExceptionControllerPriceNotFoundException {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                // Mock
                when(this.priceRepository.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(null);
                // When
                ExceptionControllerPriceNotFoundException exception = assertThrows(ExceptionControllerPriceNotFoundException.class, () -> {findApplicablePriceProxy.get(applicationDate, productId, brandId);});
                // Then
                verify(this.priceRepository).findApplicablePrice(applicationDate, productId, brandId);
                assertEquals(exception.getMessage(), "Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate);
        }


        @DisplayName("Test throw ExceptionControllerPriceParamException when applicationDate param is null")
        @Test
        public void givenApplicationProductNull_whenFindApplicablePrice_thenThrowExceptionControllerPriceParamException() {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = null;
                // When
                ExceptionControllerPriceParamException exception = assertThrows(ExceptionControllerPriceParamException.class, () -> {findApplicablePriceProxy.get(applicationDate, productId, brandId);});
                // Then
                assertEquals(exception.getMessage(), "applicationDate is required");
        }


        @DisplayName("Test throw ExceptionControllerPriceParamException when productId param is null")
        @Test
        public void givenProductIdNull_whenFindApplicablePrice_thenThrowExceptionControllerPriceParamException() {
                // Given
                Long productId = null;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                // When
                ExceptionControllerPriceParamException exception = assertThrows(ExceptionControllerPriceParamException.class, () -> {findApplicablePriceProxy.get(applicationDate, productId, brandId);});
                // Then
                assertEquals(exception.getMessage(), "productId is required");
        }


        @DisplayName("Test throw ExceptionControllerPriceParamException when brandId param is null")
        @Test
        public void givenBrandIdNull_whenFindApplicablePrice_thenThrowExceptionControllerPriceParamException() {
                // Given
                Long productId = 35455L;
                Long brandId = null;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                // When
                ExceptionControllerPriceParamException exception = assertThrows(ExceptionControllerPriceParamException.class, () -> {findApplicablePriceProxy.get(applicationDate, productId, brandId);});
                // Then
                assertEquals(exception.getMessage(), "brandId is required");
        }

}

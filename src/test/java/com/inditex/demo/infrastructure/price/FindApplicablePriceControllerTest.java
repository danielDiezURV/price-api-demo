package com.inditex.demo.infrastructure.price;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.demo.PriceApiDemoApplicationTests;
import com.inditex.demo.price.application.find.FindApplicablePrice;
import com.inditex.demo.price.domain.dto.Price;
import com.inditex.demo.shared.infrastructure.ApiError;
import com.inditex.demo.shared.infrastructure.ApiResponse;




@SpringBootTest
@AutoConfigureMockMvc
public class FindApplicablePriceControllerTest extends PriceApiDemoApplicationTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        private FindApplicablePrice findApplicablePrice;

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
                this.findApplicablePrice = mock(FindApplicablePrice.class);
        }

        
        @DisplayName("Test Controller findApplicablePrice return OK")
        @Test
        public void findApplicablePriceOK() throws Exception {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                Price mockPrice = mockPrice(1);
                String url = String.format("/prices/?applicationDate=%s&productId=%d&brandId=%d", applicationDate, productId, brandId);
                // When

                when(this.findApplicablePrice.get(applicationDate, productId, brandId)).thenReturn(mockPrice);
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();

                // Then
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);

                Price responsePrice = apiResponse.getData();
                assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                assertEquals(productId, responsePrice.getProductId());
                assertEquals(brandId, responsePrice.getBrandId());
                assertTrue(applicationDate.isAfter(responsePrice.getStartDate()) || applicationDate.isEqual(responsePrice.getStartDate()));
                assertTrue(applicationDate.isBefore(responsePrice.getEndDate()) || applicationDate.isEqual(responsePrice.getEndDate()));
                
        }


        @DisplayName("Test Controller findApplicablePrice Not Found return ApiError PriceNotFound")
        @Test
        public void givenNotFound_whenFindApplicablePriceGet_thenReturnApiErrorPriceNotFound() throws Exception {
                // Given
                Long productId = 35400L;
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                String url = String.format("/prices/?applicationDate=%s&productId=%d&brandId=%d", applicationDate, productId, brandId);

                // When
                when(this.findApplicablePrice.get(applicationDate, productId, brandId)).thenReturn(null);
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();

                // Then
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);
                ApiError apiError = apiResponse.getError();
                assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
                assertEquals("Price not found", apiError.getTitle());
                assertEquals("Price not found for product " + productId + " and brand " + brandId + " at date " + applicationDate, apiError.getDetail());
        }


        @DisplayName("Test Controller findApplicablePrice Bad Request Missing ApplicationDate return ApiError MissingRequiredParameter")
        @Test
        public void givenMissingApplicationDate_whenFindApplicablePriceGet_thenReturnApiErrorMissingRequiredParameter() throws Exception {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                String url = String.format("/prices/?productId=%d&brandId=%d", productId, brandId);

                // When
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);
                ApiError apiError = apiResponse.getError();
                assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                assertEquals("Missing Required Parameter", apiError.getTitle());
                assertEquals("Missing required parameter: 'applicationDate'", apiError.getDetail());
        }


        @DisplayName("Test Controller findApplicablePrice Bad Request Missing brandId return ApiError MissingRequiredParameter")
        @Test
        public void givenMissingBrandId_whenFindApplicablePriceGet_thenReturnApiErrorMissingRequiredParameter() throws Exception {
                // Given
                Long productId = 35455L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                String url = String.format("/prices/?applicationDate=%s&productId=%d",applicationDate, productId);

                // When
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);
                ApiError apiError = apiResponse.getError();
                assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                assertEquals("Missing Required Parameter", apiError.getTitle());
                assertEquals("Missing required parameter: 'brandId'", apiError.getDetail());
        }


        @DisplayName("Test Controller findApplicablePrice Bad Request Missing productId return ApiError MissingRequiredParameter")
        @Test
        public void givenMissingProductId_whenFindApplicablePriceGet_thenReturnApiErrorMissingRequiredParameter() throws Exception {
                // Given
                Long brandId = 1L;
                LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
                String url = String.format("/prices/?applicationDate=%s&brandId=%d", applicationDate, brandId);

                // When
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);
                ApiError apiError = apiResponse.getError();
                assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                assertEquals("Missing Required Parameter", apiError.getTitle());
                assertEquals("Missing required parameter: 'productId'", apiError.getDetail());
        }


        @DisplayName("Test Controller findApplicablePrice Bad Request Type Mismatch in applicationDate return ApiError InvalidParameterType")
        @Test
        public void givenTypeMismatchInApplicationDate_whenFindApplicablePriceGet_thenReturnApiErrorInvalidParameterType() throws Exception {
                // Given
                Long productId = 35455L;
                Long brandId = 1L;
                String applicationDate = "2024/11/08";
                String url = String.format("/prices/?applicationDate=%s&productId=%d&brandId=%d", applicationDate, productId, brandId);

                // When
                MvcResult mvcResult = mockMvc.perform(get(url)
                                             .contentType(MediaType.APPLICATION_JSON))
                                             .andReturn();
                String jsonResponse = mvcResult.getResponse().getContentAsString();
                JavaType parametricType =objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Price.class);
                ApiResponse<Price> apiResponse = objectMapper.readValue(jsonResponse, parametricType);
                ApiError apiError = apiResponse.getError();
                assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                assertEquals("Invalid Parameter Type", apiError.getTitle());
                assertEquals("Parameter 'applicationDate' should be of type 'LocalDateTime'", apiError.getDetail());
        }
}
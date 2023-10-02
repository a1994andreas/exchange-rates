package com.bv.assessment.controller;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import com.bv.assessment.service.ExchangeRatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExchangeRatesService exchangeRatesService;

	@BeforeEach
	public void beforeEach() {
		// Create a mock ExchangeRate
		ExchangeRate mockExchangeRate = new ExchangeRate(Currency.USD, Currency.EUR, new Double("0.85"));

		// Mock the service response
		Mockito.when(exchangeRatesService.getExchangeRate(Currency.USD, Currency.EUR))
				.thenReturn(mockExchangeRate);
	}

	@Test
	public void testSuccess() throws Exception {
		mockMvc.perform(get("/exchange-rates")
				.queryParam("fromCurrency", "USD")
				.queryParam("toCurrency", "EUR"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fromCurrency").value("USD"))
				.andExpect(jsonPath("$.toCurrency").value("EUR"))
				.andExpect(jsonPath("$.value").value("0.85"));
	}

	@Test
	public void testMissingParamFromCurrency() throws Exception {
		MvcResult result = mockMvc.perform(get("/exchange-rates")
				.queryParam("toCurrency", "EUR"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals(content, "Required request parameter 'fromCurrency' for method parameter type Currency is not present");
	}


	@Test
	public void testMissingParamToCurrency() throws Exception {
		MvcResult result = mockMvc.perform(get("/exchange-rates")
				.queryParam("fromCurrency", "USD"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals(content, "Required request parameter 'toCurrency' for method parameter type Currency is not present");
	}

	@Test
	public void testWrongCurrencyCode() throws Exception {
		MvcResult result = mockMvc.perform(get("/exchange-rates")
				.queryParam("toCurrency", "WRONG_CODE")
				.queryParam("fromCurrency", "EUR"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals(content, "Not supported value: WRONG_CODE provided for request parameter: toCurrency");
	}

}

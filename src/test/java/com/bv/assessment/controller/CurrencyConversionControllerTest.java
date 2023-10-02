package com.bv.assessment.controller;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.CurrencyConversion;
import com.bv.assessment.model.ExchangeRate;
import com.bv.assessment.service.CurrencyConversionService;
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

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConversionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurrencyConversionService conversionService;

	@BeforeEach
	public void beforeEach() {
		ExchangeRate mockExchangeRate = new ExchangeRate(Currency.USD, Currency.EUR, new Double("0.85"));
		CurrencyConversion currencyConversion = new CurrencyConversion(mockExchangeRate, new BigDecimal(100));

		// Mock the service response
		Mockito.when(conversionService.convertAmountToCurrency(Currency.USD, Currency.EUR, new BigDecimal(100)))
				.thenReturn(currencyConversion);


		ExchangeRate mockExchangeRateIMP = new ExchangeRate(Currency.USD, Currency.IMP, new Double("0.85"));
		CurrencyConversion currencyConversionIMP = new CurrencyConversion(mockExchangeRateIMP, new BigDecimal(100));

		List<CurrencyConversion> currencyConversions = new LinkedList<>();
		currencyConversions.add(currencyConversion);
		currencyConversions.add(currencyConversionIMP);
		List<Currency> currencies = new LinkedList<>();
		currencies.add(Currency.EUR);
		currencies.add(Currency.IMP);

		Mockito.when(conversionService.convertAmountToMultipleCurrencies(Currency.USD, currencies, new BigDecimal(100)))
				.thenReturn(currencyConversions);

	}

	@Test
	public void testSuccess() throws Exception {
		mockMvc.perform(get("/convert")
				.queryParam("fromCurrency", "USD")
				.queryParam("toCurrency", "EUR")
				.queryParam("amount", "100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.exchangeRate.fromCurrency").value("USD"))
				.andExpect(jsonPath("$.exchangeRate.toCurrency").value("EUR"))
				.andExpect(jsonPath("$.exchangeRate.value").value("0.85"))
				.andExpect(jsonPath("$.amount").value("100"));
	}

	@Test
	public void testNegativeAmount() throws Exception {
		MvcResult result = mockMvc.perform(get("/convert")
				.queryParam("fromCurrency", "USD")
				.queryParam("toCurrency", "EUR")
				.queryParam("amount", "-100"))
				.andExpect(status().isInternalServerError())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals(content, "convertValue.amount: must be greater than 0.0");
	}

	@Test
	public void testSuccessMultipleCurrencies() throws Exception {
		mockMvc.perform(get("/convert/multiple-currencies")
				.queryParam("fromCurrency", "USD")
				.queryParam("toCurrencies", "EUR,IMP")
				.queryParam("amount", "100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].exchangeRate.fromCurrency").value("USD"))
				.andExpect(jsonPath("$[0].exchangeRate.toCurrency").value("EUR"))
				.andExpect(jsonPath("$[0].exchangeRate.value").value("0.85"))
				.andExpect(jsonPath("$[0].amount").value("100"))
				.andExpect(jsonPath("$[1].exchangeRate.fromCurrency").value("USD"))
				.andExpect(jsonPath("$[1].exchangeRate.toCurrency").value("IMP"))
				.andExpect(jsonPath("$[1].exchangeRate.value").value("0.85"))
				.andExpect(jsonPath("$[1].amount").value("100"));
	}

	@Test
	public void testWrongCommaSeparatedCurrencies() throws Exception {
		MvcResult result = mockMvc.perform(get("/convert/multiple-currencies")
				.queryParam("fromCurrency", "USD")
				.queryParam("toCurrencies", "EUR-IMP")
				.queryParam("amount", "100"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals(content, "Not supported value: EUR-IMP provided for request parameter: toCurrencies");
	}

}

package com.bv.assessment.utils;

import com.bv.assessment.client.ex.ExchangeRatesClient;
import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRatesProvider {
	private ExchangeRatesClient exchangeRatesClient;

	public ExchangeRatesProvider(@Qualifier("exchangeRatesHostClient") ExchangeRatesClient exchangeRatesClient) {
		this.exchangeRatesClient = exchangeRatesClient;
	}

	@Cacheable(value = "exchangeRates")
	public ExchangeRate getExchangeRate(Currency baseCurrency, Currency toCurrency) {
		return exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);
	}

	@Cacheable(value = "exchangeRates")
	public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency baseCurrency) {
		return exchangeRatesClient.getAllExchangeRatesForCurrency(baseCurrency);
	}
}

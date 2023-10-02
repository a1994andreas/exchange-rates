package com.bv.assessment.utils;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRatesCache {

	private ExchangeRatesProvider exchangeRatesProvider;

	public ExchangeRatesCache(ExchangeRatesProvider exchangeRatesProvider) {
		this.exchangeRatesProvider = exchangeRatesProvider;
	}

	@Cacheable(value = "exchangeRates")
	public ExchangeRate getExchangeRate(Currency baseCurrency, Currency toCurrency) {
		return exchangeRatesProvider.pickHealthyClient().getExchangeRate(baseCurrency, toCurrency);
	}

	@Cacheable(value = "exchangeRates")
	public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency baseCurrency) {
		return exchangeRatesProvider.pickHealthyClient().getAllExchangeRatesForCurrency(baseCurrency);
	}
}

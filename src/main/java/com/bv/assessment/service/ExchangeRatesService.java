package com.bv.assessment.service;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import com.bv.assessment.utils.ExchangeRatesCache;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExchangeRatesService {

	private ExchangeRatesCache exchangeRatesCache;

	public ExchangeRatesService (ExchangeRatesCache exchangeRatesCache, RestTemplate restTemplate) {
		this.exchangeRatesCache = exchangeRatesCache;
	}

	public ExchangeRate getExchangeRate(Currency fromCurrency, Currency toCurrency) {
		return exchangeRatesCache.getExchangeRate(fromCurrency, toCurrency);
	}

	public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency fromCurrency) {
		return exchangeRatesCache.getAllExchangeRatesForCurrency(fromCurrency);
	}
}

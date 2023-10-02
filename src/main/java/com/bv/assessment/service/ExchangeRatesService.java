package com.bv.assessment.service;

import com.bv.assessment.utils.ExchangeRatesProvider;
import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ExchangeRatesService {

	private ExchangeRatesProvider exchangeRatesProvider;

	public ExchangeRatesService (ExchangeRatesProvider exchangeRatesProvider, RestTemplate restTemplate) {
		this.exchangeRatesProvider = exchangeRatesProvider;
	}

	public ExchangeRate getExchangeRate(Currency fromCurrency, Currency toCurrency) {
		return exchangeRatesProvider.getExchangeRate(fromCurrency, toCurrency);
	}

	public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency fromCurrency) {
		return exchangeRatesProvider.getAllExchangeRatesForCurrency(fromCurrency);
	}
}

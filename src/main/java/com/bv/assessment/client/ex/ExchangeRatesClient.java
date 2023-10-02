package com.bv.assessment.client.ex;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;

import java.util.List;
import java.util.Map;

public interface ExchangeRatesClient {
	ExchangeRate getExchangeRate(Currency baseCurrency, Currency toCurrency);

	List<ExchangeRate> getAllExchangeRatesForCurrency(Currency baseCurrency);

	String getBaseUrl();
}

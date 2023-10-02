package com.bv.assessment.service;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.CurrencyConversion;
import com.bv.assessment.model.ExchangeRate;
import com.bv.assessment.utils.ExchangeRatesCache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyConversionService {

	private ExchangeRatesCache exchangeRatesCache;

	public CurrencyConversionService(ExchangeRatesCache exchangeRatesCache) {
		this.exchangeRatesCache = exchangeRatesCache;
	}

	public CurrencyConversion convertAmountToCurrency(Currency fromCurrency, Currency toCurrency, BigDecimal amount) {
		ExchangeRate exchangeRate = exchangeRatesCache.getExchangeRate(fromCurrency, toCurrency);
		return new CurrencyConversion(exchangeRate, calculateAmount(exchangeRate.getValue(), amount));
	}

	public List<CurrencyConversion> convertAmountToMultipleCurrencies(Currency fromCurrency, List<Currency> toCurrencies, BigDecimal amount) {
		List<ExchangeRate> exchangeRates = exchangeRatesCache.getAllExchangeRatesForCurrency(fromCurrency);
		return exchangeRates.stream()
				.filter(e -> toCurrencies.contains(e.getToCurrency()))
				.map( e -> new CurrencyConversion(e,calculateAmount(e.getValue(), amount)))
				.collect(Collectors.toList());
	}

	private BigDecimal calculateAmount(Double value, BigDecimal amount) {
		BigDecimal exchangeRateValue = BigDecimal.valueOf(value);
		return exchangeRateValue.multiply(amount).setScale(2, RoundingMode.HALF_UP);
	}
}

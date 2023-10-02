package com.bv.assessment.client.ex;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Based on doc https://exchangerate.host/documentation
 * There was a recent change on this api and the only supported
 * source on free version is USD.
 * Quote from their API:
 * 	* "source:  [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher."
 * So i had to adapt my solution  and request the exchange rates in USD and then do the conversions.
 */
@Component
public class ExchangeRatesHostClient implements ExchangeRatesClient, Serializable {
	private RestTemplate restTemplate;
	private static final String BASE_EXCHANGE_RATES_URL = "http://api.exchangerate.host/live";
	private static final String ACCESS_KEY = "a3185b0c6212b372700fbc763ca585f6";
	private static final String BASE_CURRENCY = "USD";

	public ExchangeRatesHostClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override public ExchangeRate getExchangeRate(Currency baseCurrency, Currency toCurrency) {
		Map<String,Double> exchangeRatesUsd = getExchangeRatesInUsd(generateURLForCurrency(baseCurrency, toCurrency));

		Double baseCurrencyToUsd = exchangeRatesUsd.get(baseCurrency.name());
		Double otherCurrencyToUsd = exchangeRatesUsd.get(toCurrency.name());

		return new ExchangeRate(baseCurrency, toCurrency, calculateExchangeRate(otherCurrencyToUsd, baseCurrencyToUsd ));

	}

	@Override public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency baseCurrency) {
		Map<String,Double> exchangeRatesUsd = getExchangeRatesInUsd(generateURL());

		Double baseCurrencyToUsd = exchangeRatesUsd.get(baseCurrency.name());
		return exchangeRatesUsd.entrySet().stream()
				.filter(e -> Arrays.stream(Currency.values()).filter( c->c.name().equals(e.getKey())).findFirst().isPresent()) // filter supported currencies
				.map( e -> new ExchangeRate(baseCurrency, Currency.valueOf(e.getKey()), calculateExchangeRate(e.getValue(), baseCurrencyToUsd)))
				.collect(Collectors.toList());
	}

	@Override public String getBaseUrl() {
		return BASE_EXCHANGE_RATES_URL;
	}

	private Map<String, Double> getExchangeRatesInUsd(String url) {
		ResponseEntity<String> exchangeRatesResponse = restTemplate.getForEntity(url, String.class);
		JSONObject jsonObject = new JSONObject(exchangeRatesResponse.getBody());
		JSONObject conversionRates = jsonObject.getJSONObject("quotes");
		Map<String,Double> exchangeRatesUsd = conversionRates.toMap().entrySet().stream()
				.collect(Collectors.toMap( e -> e.getKey().substring(BASE_CURRENCY.length())
						, e -> new Double(e.getValue().toString())));

		// Since the base currency will always be USD for this provider, we
		// need to add the EX from USD to USD manually
		exchangeRatesUsd.put(Currency.USD.name(), 1d);
		return exchangeRatesUsd;
	}

	/*
	* In this method we are getting the exchange rate of requested currency (e.g. AED) to USD
	* and the ex of any other currency (e.g. BGN) to USD
	* And we use those values to calculate the exchange rate from requested currency to any other currency (e.g. AED to BGN)
	*
	* (This was done because the client supports only USD as source on free version. Quote from their API:
	* "source:  [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher."
	 * */
	private Double calculateExchangeRate(Double newCurrencyToUsd, Double baseCurrencyToUsd) {
		return newCurrencyToUsd/baseCurrencyToUsd;
	}

	private String generateURL() {
		return new StringBuilder(BASE_EXCHANGE_RATES_URL)
				.append("?access_key=")
				.append(ACCESS_KEY).toString();
	}

	private String generateURLForCurrency(Currency baseCurrency, Currency toCurrency) {
		return new StringBuilder(generateURL())
				.append("&currencies=")
				.append(baseCurrency.name())
				.append(",")
				.append(toCurrency.name())
				.toString();
	}
}

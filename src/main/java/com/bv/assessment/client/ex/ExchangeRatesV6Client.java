package com.bv.assessment.client.ex;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* Based on doc https://www.exchangerate-api.com/docs/standard-requests
 * This client supports multiple base currencies but it always return all
 * the available exchange rates. So the filtering was done in our side.
* */
@Component
public class ExchangeRatesV6Client implements ExchangeRatesClient {

	private RestTemplate restTemplate;
	private static final String BASE_EXCHANGE_RATES_URL = "https://v6.exchangerate-api.com/v6/";
	private static final String ACCESS_KEY = "f0e506896c2b20379abe810d";

	public ExchangeRatesV6Client(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override public ExchangeRate getExchangeRate(Currency baseCurrency, Currency toCurrency) {
		List<ExchangeRate> exchangeRates = getAllExchangeRatesForCurrency(baseCurrency);

		Optional<ExchangeRate> exchangeRateOptional = exchangeRates.stream().filter( e -> toCurrency.equals(e.getToCurrency())).findFirst();

		return exchangeRateOptional.isPresent() ? exchangeRateOptional.get() : null;
	}

	@Override public List<ExchangeRate> getAllExchangeRatesForCurrency(Currency baseCurrency) {
		ResponseEntity<String> exchangeRatesResponse = restTemplate.getForEntity(generateURL(baseCurrency), String.class);

		JSONObject jsonObject = new JSONObject(exchangeRatesResponse.getBody());
		JSONObject conversionRates = jsonObject.getJSONObject("conversion_rates");

		List<ExchangeRate> exchangeRates = conversionRates.toMap().entrySet().stream()
				.map( e -> new ExchangeRate(baseCurrency, Currency.valueOf(e.getKey()), new Double(e.getValue().toString())))
				.collect(Collectors.toList());
		return exchangeRates;

	}

	private String generateURL(Currency baseCurrency) {
		return BASE_EXCHANGE_RATES_URL + ACCESS_KEY + "/latest/"+ baseCurrency;
	}
}

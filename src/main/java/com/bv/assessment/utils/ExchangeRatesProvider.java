package com.bv.assessment.utils;

import com.bv.assessment.client.ex.ExchangeRatesClient;
import com.bv.assessment.exception.NoClientsAvailableException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesProvider {
	private ExchangeRatesClient exchangeRatesHostClient;
	private ExchangeRatesClient exchangeRatesV6Client;
	private CheckConnectionUtil checkConnectionUtil;

	public ExchangeRatesProvider(@Qualifier("exchangeRatesHostClient") ExchangeRatesClient exchangeRatesHostClient,
			@Qualifier("exchangeRatesV6Client") ExchangeRatesClient exchangeRatesV6Client, CheckConnectionUtil checkConnectionUtil) {
		this.exchangeRatesHostClient= exchangeRatesHostClient;
		this.exchangeRatesV6Client = exchangeRatesV6Client;
		this.checkConnectionUtil = checkConnectionUtil;
	}

	@SneakyThrows
	public ExchangeRatesClient pickHealthyClient() {
		if (checkConnectionUtil.checkConnection(exchangeRatesHostClient.getBaseUrl())) {
			return exchangeRatesHostClient;
		} else if (checkConnectionUtil.checkConnection(exchangeRatesV6Client.getBaseUrl())) {
			return exchangeRatesV6Client;
		} else {
			throw new NoClientsAvailableException("Service not available");
		}
	}
}

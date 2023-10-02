package com.bv.assessment.controller;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import com.bv.assessment.service.ExchangeRatesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/exchange-rates")
public class ExchangeRateController {

	private ExchangeRatesService exchangeRatesService;

	public ExchangeRateController (ExchangeRatesService exchangeRatesService) {
		this.exchangeRatesService = exchangeRatesService;
	}

	@ApiOperation(value = "Get the exchange rate between currencies")
	@GetMapping
	public ExchangeRate getExchangeRate(@RequestParam(name = "fromCurrency") @NotNull Currency fromCurrency, @RequestParam(name = "toCurrency") @NotNull Currency toCurrency) {
		return exchangeRatesService.getExchangeRate(fromCurrency, toCurrency);
	}

	@ApiOperation(value = "Get the exchange rate between all available currencies")
	@GetMapping("/all")
	public List<ExchangeRate> getAllExchangeRatesForCurrency(@RequestParam(name = "currency") @NotNull Currency currency) {
		return exchangeRatesService.getAllExchangeRatesForCurrency(currency);
	}

}

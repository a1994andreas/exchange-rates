package com.bv.assessment.controller;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.CurrencyConversion;
import com.bv.assessment.service.CurrencyConversionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/convert")
public class CurrencyConversionController {

	private CurrencyConversionService conversionService;

	public CurrencyConversionController(CurrencyConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@ApiOperation(value = "Convert amount to a different currency using live exchange rates")
	@GetMapping
	public CurrencyConversion convertValue(@RequestParam(name = "fromCurrency") @NotNull Currency fromCurrency, @RequestParam(name = "toCurrency") @NotNull Currency toCurrency,
			@RequestParam(name = "amount") @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {
		return conversionService.convertAmountToCurrency(fromCurrency, toCurrency, amount);
	}

	@ApiOperation(value = "Convert amount to multiple currencies using live exchange rates")
	@GetMapping("/multiple-currencies")
	public List<CurrencyConversion> convertValueToMultipleCurrencies(@RequestParam(name = "fromCurrency") @NotNull Currency fromCurrency,
			@RequestParam(name = "toCurrencies") @NotNull List<Currency> toCurrencies, @RequestParam(name = "amount")  @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {
		return conversionService.convertAmountToMultipleCurrencies(fromCurrency, toCurrencies, amount);
	}
}

package com.bv.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyConversion {
	private ExchangeRate exchangeRate;
	private BigDecimal amount;
}

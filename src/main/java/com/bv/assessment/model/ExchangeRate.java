package com.bv.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRate implements Serializable {
	private Currency fromCurrency;
	private Currency toCurrency;
	private Double value;
}

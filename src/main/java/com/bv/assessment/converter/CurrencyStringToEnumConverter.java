package com.bv.assessment.converter;

import com.bv.assessment.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyStringToEnumConverter implements Converter<String, Currency> {
	@Override
	public Currency convert(String source) {
		try {
			return Currency.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}

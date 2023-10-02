package com.bv.assessment.converter;

import com.bv.assessment.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class CurrencyStringToEnumListConverter implements Converter<String, List<Currency>> {
	@Override
	public List<Currency> convert(String source) {
		try {
			List<Currency> currencies = new LinkedList<>();
			Arrays.stream(source.split(",")).forEach(s -> currencies.add( Currency.valueOf(s.toUpperCase())));
			return currencies;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}

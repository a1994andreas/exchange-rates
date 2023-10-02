package com.bv.assessment.client.ex;

import com.bv.assessment.model.Currency;
import com.bv.assessment.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRatesV6ClientTest {
	@Mock
	private RestTemplate restTemplate;

	private ExchangeRatesClient exchangeRatesClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		exchangeRatesClient = new ExchangeRatesV6Client(restTemplate);

		// Mock the response from the REST service
		String responseBody = "{ \"result\":\"success\", \"documentation\":\"https://www.exchangerate-api.com/docs\", \"terms_of_use\":\"https://www.exchangerate-api.com/terms\", \"time_last_update_unix\":1696204801, \"time_last_update_utc\":\"Mon, 02 Oct 2023 00:00:01 +0000\", \"time_next_update_unix\":1696291201, \"time_next_update_utc\":\"Tue, 03 Oct 2023 00:00:01 +0000\", \"base_code\":\"BMD\", \"conversion_rates\":{ \"BMD\":1, \"AED\":3.6725, \"AFN\":78.0092, \"ALL\":100.7427, \"AMD\":394.0634, \"ANG\":1.7900, \"AOA\":833.3652, \"ARS\":350.0100, \"AUD\":1.5541, \"AWG\":1.7900, \"AZN\":1.7038, \"BAM\":1.8504, \"BBD\":2.0000, \"BDT\":110.2600, \"BGN\":1.8504, \"BHD\":0.3760, \"BIF\":2832.7836, \"BND\":1.3645, \"BOB\":6.9211, \"BRL\":5.0202, \"BSD\":1.0000, \"BTN\":83.2366, \"BWP\":13.7122, \"BYN\":2.9862, \"BZD\":2.0000, \"CAD\":1.3561, \"CDF\":2482.5821, \"CHF\":0.9153, \"CLP\":899.2767, \"CNY\":7.2987, \"COP\":4067.9045, \"CRC\":535.3513, \"CUP\":24.0000, \"CVE\":104.3227, \"CZK\":23.0311, \"DJF\":177.7210, \"DKK\":7.0583, \"DOP\":56.7555, \"DZD\":137.5262, \"EGP\":30.9218, \"ERN\":15.0000, \"ETB\":55.6678, \"EUR\":0.9461, \"FJD\":2.2962, \"FKP\":0.8198, \"FOK\":7.0583, \"GBP\":0.8204, \"GEL\":2.6763, \"GGP\":0.8198, \"GHS\":11.6147, \"GIP\":0.8198, \"GMD\":64.8238, \"GNF\":8583.2451, \"GTQ\":7.8604, \"GYD\":209.2065, \"HKD\":7.8312, \"HNL\":24.7508, \"HRK\":7.1285, \"HTG\":135.5165, \"HUF\":368.2358, \"IDR\":15465.9685, \"ILS\":3.8155, \"IMP\":0.8198, \"INR\":83.2368, \"IQD\":1309.3447, \"IRR\":41924.6312, \"ISK\":136.6564, \"JEP\":0.8198, \"JMD\":154.8323, \"JOD\":0.7090, \"JPY\":149.4470, \"KES\":148.2695, \"KGS\":88.6717, \"KHR\":4119.7626, \"KID\":1.5537, \"KMF\":465.4551, \"KRW\":1349.5655, \"KWD\":0.3092, \"KYD\":0.8333, \"KZT\":477.8858, \"LAK\":20443.3563, \"LBP\":15000.0000, \"LKR\":323.6214, \"LRD\":189.3026, \"LSL\":18.8675, \"LYD\":4.8880, \"MAD\":10.2818, \"MDL\":18.2035, \"MGA\":4544.2690, \"MKD\":58.0533, \"MMK\":2101.0145, \"MNT\":3470.6427, \"MOP\":8.0662, \"MRU\":38.0579, \"MUR\":44.4761, \"MVR\":15.4560, \"MWK\":1087.7574, \"MXN\":17.4241, \"MYR\":4.6960, \"MZN\":63.8383, \"NAD\":18.8675, \"NGN\":774.6500, \"NIO\":36.7420, \"NOK\":10.6867, \"NPR\":133.1785, \"NZD\":1.6667, \"OMR\":0.3845, \"PAB\":1.0000, \"PEN\":3.7892, \"PGK\":3.6522, \"PHP\":56.6727, \"PKR\":288.1717, \"PLN\":4.3666, \"PYG\":7325.7281, \"QAR\":3.6400, \"RON\":4.6947, \"RSD\":110.7147, \"RUB\":97.5903, \"RWF\":1220.0566, \"SAR\":3.7500, \"SBD\":8.4217, \"SCR\":14.0397, \"SDG\":491.0995, \"SEK\":10.9260, \"SGD\":1.3645, \"SHP\":0.8198, \"SLE\":20.5939, \"SLL\":20593.9427, \"SOS\":571.1874, \"SRD\":38.6326, \"SSP\":1012.3761, \"STN\":23.1797, \"SYP\":12948.4331, \"SZL\":18.8675, \"THB\":36.5400, \"TJS\":10.9359, \"TMT\":3.4996, \"TND\":3.1671, \"TOP\":2.3498, \"TRY\":27.4732, \"TTD\":6.7820, \"TVD\":1.5537, \"TWD\":32.1999, \"TZS\":2512.1579, \"UAH\":36.9280, \"UGX\":3766.2239, \"USD\":1.0000, \"UYU\":38.3895, \"UZS\":12338.8057, \"VES\":34.4254, \"VND\":24323.7962, \"VUV\":121.0778, \"WST\":2.7737, \"XAF\":620.6068, \"XCD\":2.7000, \"XDR\":0.7593, \"XOF\":620.6068, \"XPF\":112.9011, \"YER\":250.4507, \"ZAR\":18.8676, \"ZMW\":20.9987, \"ZWL\":5380.3515 } },[Date:\"Mon, 02 Oct 2023 10:39:28 GMT\", Content-Type:\"application/json\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", Access-Control-Allow-Headers:\"*\", Access-Control-Allow-Origin:\"*\", X-Content-Type-Options:\"NOSNIFF\", X-Frame-Options:\"SAMEORIGIN\", CF-Cache-Status:\"DYNAMIC\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report\\/v3?s=FVLY2UnxOPbPRmEq%2ByAVJ6i65M8m0QQ1jReNfVgFIM8O8rqfUuR0AwEpFGWj1tso%2BncCiylTXttbKj0fvoKwi75pikjG67DYqqyfPDwoILsAp2pXiHFn2hkHyA%2B2LVO1wMgP%2FStKpbiq\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"success_fraction\":0,\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"80fc5257ae456f50-ATH\", alt-svc:\"h3=\":443\"; ma=86400\"]";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
		Mockito.when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

	}


	@Test
	public void testGetExchangeRateUSDToJPY() {
		// Test the getExchangeRate method
		Currency baseCurrency = Currency.USD;
		Currency toCurrency = Currency.JPY;
		ExchangeRate exchangeRate = exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);

		// Verify
		assertEquals(baseCurrency, exchangeRate.getFromCurrency());
		assertEquals(toCurrency, exchangeRate.getToCurrency());
		assertEquals(149.447, exchangeRate.getValue());
	}



	@Test
	public void testGetExchangeRateEURToJPY() {
		Currency baseCurrency = Currency.EUR;
		Currency toCurrency = Currency.JPY;
		ExchangeRate exchangeRate = exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);

		assertEquals(baseCurrency, exchangeRate.getFromCurrency());
		assertEquals(toCurrency, exchangeRate.getToCurrency());
		assertEquals(149.447, exchangeRate.getValue());
	}

	@Test
	public void testGetAllExchangeRatesEUR() {
		Currency baseCurrency = Currency.EUR;
		List<ExchangeRate> exchangeRates = exchangeRatesClient.getAllExchangeRatesForCurrency(baseCurrency);

		ExchangeRate exchangeRate1 = new ExchangeRate(baseCurrency, Currency.USD, new Double(1.0));


		Optional<ExchangeRate> exceptionOptional = exchangeRates.stream().filter( e -> e.getToCurrency().equals(exchangeRate1.getToCurrency())).findFirst();

		assertTrue(exceptionOptional.isPresent());

		assertEquals(exceptionOptional.get().getFromCurrency(), exchangeRate1.getFromCurrency());
		assertEquals(exceptionOptional.get().getToCurrency(), exchangeRate1.getToCurrency());
		assertEquals(exceptionOptional.get().getValue(), exchangeRate1.getValue());
		assertEquals(exchangeRates.size(), 162);
	}

}

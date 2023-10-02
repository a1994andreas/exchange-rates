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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRatesHostClientTest {
	@Mock
	private RestTemplate restTemplate;

	private ExchangeRatesClient exchangeRatesClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		exchangeRatesClient = new ExchangeRatesHostClient(restTemplate);
	}

	@Test
	public void testGetExchangeRateUSDToJPY() {
		// Mock the response from the REST service
		String responseBody = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1696193583,\"source\":\"USD\",\"quotes\":{\"USDJPY\":149.627497}},[Date:\"Sun, 01 Oct 2023 20:53:59 GMT\", Content-Type:\"application/json; Charset=UTF-8\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", x-apilayer-transaction-id:\"51442c46-58c0-4bc3-9b93-30a7df32ce9f\", access-control-allow-methods:\"GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS\", last-modified:\"Sun, 01 Oct 2023 20:53:03 GMT\", etag:\"f8ba3dca7daaf18924d12e9f26e16a53\", access-control-allow-origin:\"*\", x-request-time:\"0.009\", CF-Cache-Status:\"DYNAMIC\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report\\/v3?s=zZgP44%2Fd%2BM5ok6HvWRBYtrbfIwXKWKjVugtUVY%2B1DT7Dd9PDVUAQMiloqhNmfMhF56E853MTrgiWftLem4EK1gaIhDvBmc87DhZrRaqKC1acZGx6JQCk9DqVE%2Fe3lsN%2BLeEzSnva1g%3D%3D\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"success_fraction\":0,\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"80f79922aab7ef00-ATH\"]";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
		Mockito.when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

		// Test the getExchangeRate method
		Currency baseCurrency = Currency.USD;
		Currency toCurrency = Currency.JPY;
		ExchangeRate exchangeRate = exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);

		// Verify
		assertEquals(baseCurrency, exchangeRate.getFromCurrency());
		assertEquals(toCurrency, exchangeRate.getToCurrency());
		assertEquals(149.627497, exchangeRate.getValue());
	}

	@Test
	public void testGetExchangeRateEURToJPY() {
		String responseBody = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1696194543,\"source\":\"USD\",\"quotes\":{\"USDEUR\":0.945995,\"USDJPY\":149.62497}},[Date:\"Sun, 01 Oct 2023 21:09:21 GMT\", Content-Type:\"application/json; Charset=UTF-8\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", x-apilayer-transaction-id:\"4788b72e-0c5f-4393-ab2b-fe9875a4dfd0\", access-control-allow-methods:\"GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS\", last-modified:\"Sun, 01 Oct 2023 21:09:03 GMT\", etag:\"a80b35bb154228d135cd739e7741f0f8\", access-control-allow-origin:\"*\", x-request-time:\"0.009\", CF-Cache-Status:\"DYNAMIC\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report\\/v3?s=gTe3%2F6DSF9CRV6PpgcWl%2FX8%2Fd3AoEk1oeQgWxFQm651eUuV00Wm1T6poJmJs5hvPU4mhUdrTqUJ%2Bpr3Y0rftvfqGTmvQ8DqrBRjAigD88OdOquqQc1F60hPFXpHI2xNtmLWWGWeebg%3D%3D\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"success_fraction\":0,\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"80f7afa6592a38d7-ATH\"]";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
		Mockito.when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

		Currency baseCurrency = Currency.EUR;
		Currency toCurrency = Currency.JPY;
		ExchangeRate exchangeRate = exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);

		assertEquals(baseCurrency, exchangeRate.getFromCurrency());
		assertEquals(toCurrency, exchangeRate.getToCurrency());
		assertEquals(158.16676620912372, exchangeRate.getValue());
	}

	@Test
	public void testGetExchangeRateEURToEUR() {
		String responseBody = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1696194663,\"source\":\"USD\",\"quotes\":{\"USDEUR\":0.946014}},[Date:\"Sun, 01 Oct 2023 21:11:56 GMT\", Content-Type:\"application/json; Charset=UTF-8\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", x-apilayer-transaction-id:\"1b08e1dd-9eb4-4ceb-9737-55e7ef809b03\", access-control-allow-methods:\"GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS\", last-modified:\"Sun, 01 Oct 2023 21:11:03 GMT\", etag:\"21262062469005cc7f3a1c6d4c742d83\", access-control-allow-origin:\"*\", x-request-time:\"0.009\", CF-Cache-Status:\"DYNAMIC\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report\\/v3?s=iiC7l5df36PHn32fc4OykmJmHK97ANtIXCqQz5NHdgdiOdb%2BKB8ZgiK4RsNVSEIK8Z8Uk611vNrEsFibppTLOZHWJXujm6VMvFa59AvM%2BmlFziwnIa0QYlmfab%2F0LIcSODbt8xZgYQ%3D%3D\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"success_fraction\":0,\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"80f7b36e9998ef04-ATH\"]";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
		Mockito.when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

		Currency baseCurrency = Currency.EUR;
		Currency toCurrency = Currency.EUR;
		ExchangeRate exchangeRate = exchangeRatesClient.getExchangeRate(baseCurrency, toCurrency);

		assertEquals(baseCurrency, exchangeRate.getFromCurrency());
		assertEquals(toCurrency, exchangeRate.getToCurrency());
		assertEquals(1.0, exchangeRate.getValue());
	}

	@Test
	public void testGetAllExchangeRatesEUR() {
		String responseBody = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1696240743,\"source\":\"USD\",\"quotes\":{\"USDAED\":3.67297,\"USDAFN\":78.291983,\"USDALL\":101.008189,\"USDAMD\":397.945902,\"USDANG\":1.810101,\"USDAOA\":830.00015,\"USDARS\":350.001708,\"USDAUD\":1.563135,\"USDAWG\":1.8025,\"USDAZN\":1.702537,\"USDBAM\":1.851338,\"USDBBD\":2.027832,\"USDBDT\":110.722484,\"USDBGN\":1.84991,\"USDBHD\":0.377008,\"USDBIF\":2847.16306,\"USDBMD\":1,\"USDBND\":1.367922,\"USDBOB\":6.939442,\"USDBRL\":5.032404,\"USDBSD\":1.004307,\"USDBTC\":3.5292096e-5,\"USDBTN\":83.397307,\"USDBWP\":13.701898,\"USDBYN\":2.53487,\"USDBYR\":19600,\"USDBZD\":2.024424,\"USDCAD\":1.36098,\"USDCDF\":2492.999934,\"USDCHF\":0.912755,\"USDCLF\":0.032296,\"USDCLP\":891.149779,\"USDCNY\":7.177297,\"USDCOP\":4077.67,\"USDCRC\":536.907669,\"USDCUC\":1,\"USDCUP\":26.5,\"USDCVE\":104.375606,\"USDCZK\":23.188402,\"USDDJF\":178.823307,\"USDDKK\":7.07877,\"USDDOP\":57.013309,\"USDDZD\":137.443981,\"USDEGP\":30.9011,\"USDERN\":15,\"USDETB\":55.960035,\"USDEUR\":0.949095,\"USDFJD\":2.26865,\"USDFKP\":0.819723,\"USDGBP\":0.822135,\"USDGEL\":2.685012,\"USDGGP\":0.819723,\"USDGHS\":11.643988,\"USDGIP\":0.819723,\"USDGMD\":64.374951,\"USDGNF\":8621.611772,\"USDGTQ\":7.893603,\"USDGYD\":210.104835,\"USDHKD\":7.83265,\"USDHNL\":24.767611,\"USDHRK\":7.014709,\"USDHTG\":136.08224,\"USDHUF\":367.612973,\"USDIDR\":15556.1,\"USDILS\":3.83112,\"USDIMP\":0.819723,\"USDINR\":83.193303,\"USDIQD\":1315.190383,\"USDIRR\":42237.503834,\"USDISK\":138.669832,\"USDJEP\":0.819723,\"USDJMD\":155.659261,\"USDJOD\":0.709903,\"USDJPY\":149.733048,\"USDKES\":148.304156,\"USDKGS\":88.710267,\"USDKHR\":4139.452112,\"USDKMF\":465.999964,\"USDKPW\":900,\"USDKRW\":1355.695017,\"USDKWD\":0.30914,\"USDKYD\":0.836879,\"USDKZT\":479.79743,\"USDLAK\":20475.660837,\"USDLBP\":15094.019926,\"USDLKR\":325.635989,\"USDLRD\":186.711502,\"USDLSL\":18.919859,\"USDLTL\":2.95274,\"USDLVL\":0.60489,\"USDLYD\":4.909358,\"USDMAD\":10.296519,\"USDMDL\":18.327851,\"USDMGA\":4567.6149,\"USDMKD\":58.266911,\"USDMMK\":2109.054764,\"USDMNT\":3461.518943,\"USDMOP\":8.100906,\"USDMRO\":356.999828,\"USDMUR\":44.475027,\"USDMVR\":15.349664,\"USDMWK\":1085.719695,\"USDMXN\":17.47148,\"USDMYR\":4.717015,\"USDMZN\":63.24995,\"USDNAD\":18.919926,\"USDNGN\":769.550268,\"USDNIO\":36.746748,\"USDNOK\":10.71228,\"USDNPR\":133.436512,\"USDNZD\":1.674495,\"USDOMR\":0.384984,\"USDPAB\":1.004236,\"USDPEN\":3.805967,\"USDPGK\":3.668961,\"USDPHP\":56.79428,\"USDPKR\":290.000568,\"USDPLN\":4.374486,\"USDPYG\":7329.956927,\"USDQAR\":3.641027,\"USDRON\":4.721098,\"USDRSD\":111.235025,\"USDRUB\":98.4675,\"USDRWF\":1226.228453,\"USDSAR\":3.750623,\"USDSBD\":8.383555,\"USDSCR\":13.349655,\"USDSDG\":598.363554,\"USDSEK\":10.98246,\"USDSGD\":1.371815,\"USDSHP\":1.21675,\"USDSLE\":22.539891,\"USDSLL\":19750.000327,\"USDSOS\":569.500648,\"USDSRD\":38.322496,\"USDSTD\":20697.981008,\"USDSSP\":601.497181,\"USDSYP\":13001.855243,\"USDSZL\":18.918919,\"USDTHB\":36.940498,\"USDTJS\":11.008643,\"USDTMT\":3.51,\"USDTND\":3.156498,\"USDTOP\":2.38935,\"USDTRY\":27.46565,\"USDTTD\":6.815439,\"USDTWD\":32.256299,\"USDTZS\":2512.00004,\"USDUAH\":37.091494,\"USDUGX\":3776.889036,\"USDUYU\":38.630286,\"USDUZS\":12251.75593,\"USDVEF\":3422038.790166,\"USDVES\":34.258052,\"USDVND\":24355,\"USDVUV\":121.062687,\"USDWST\":2.773179,\"USDXAF\":620.930321,\"USDXAG\":0.046355,\"USDXAU\":0.000545,\"USDXCD\":2.70255,\"USDXDR\":0.765939,\"USDXOF\":620.965589,\"USDXPF\":113.349977,\"USDYER\":250.395715,\"USDZAR\":19.033351,\"USDZMK\":9001.202394,\"USDZMW\":21.015762,\"USDZWL\":321.999592}},[Date:\"Mon, 02 Oct 2023 09:59:56 GMT\", Content-Type:\"application/json; Charset=UTF-8\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", x-apilayer-transaction-id:\"eec2f08f-b186-41ab-b55e-21156810baef\", access-control-allow-methods:\"GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS\", last-modified:\"Mon, 02 Oct 2023 09:59:03 GMT\", etag:\"83e61254655698952d1c767e61abea96\", access-control-allow-origin:\"*\", x-request-time:\"0.009\", CF-Cache-Status:\"DYNAMIC\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report\\/v3?s=U0vv4hSZaCfVmCG96MnV44Oy4qPiz7moi5BU1y7eeQdblyM0foHTMRZuwY0OHJVFULCP8SYFf8IM63Ito%2FsbC7xbqxbVOOSyCp%2BHvpysOa%2FqDNqsTaLHp%2BzByLmO2qSl82NNUa0FTw%3D%3D\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"success_fraction\":0,\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"80fc18724e3138cf-ATH\"]";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
		Mockito.when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

		Currency baseCurrency = Currency.EUR;
		List<ExchangeRate> exchangeRates = exchangeRatesClient.getAllExchangeRatesForCurrency(baseCurrency);

		ExchangeRate exchangeRate1 = new ExchangeRate(baseCurrency, Currency.USD, new Double(1.053635305211807));


		Optional<ExchangeRate> exceptionOptional = exchangeRates.stream().filter( e -> e.getToCurrency().equals(exchangeRate1.getToCurrency())).findFirst();

		assertTrue(exceptionOptional.isPresent());

		assertEquals(exceptionOptional.get().getFromCurrency(), exchangeRate1.getFromCurrency());
		assertEquals(exceptionOptional.get().getToCurrency(), exchangeRate1.getToCurrency());
		assertEquals(exceptionOptional.get().getValue(), exchangeRate1.getValue());
		assertEquals(exchangeRates.size(), 166);
	}
}

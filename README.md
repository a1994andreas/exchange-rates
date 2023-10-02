# Exchange Rates


## Solution
### 
- I have integrated with two exchange rates clients. Based on those two providers:
- - https://exchangerate.host/documentation
- - https://www.exchangerate-api.com/docs/standard-requests
- I have wrapped those clients in ExchangeRatesProvider that checks in runtime the connectivity and 
  picks a client.
- - The connectivity response is cached for 10 seconds to avoid unnecessary calls on the client
- I have used Redis for caching with a ttl of 60 seconds.
- - ExchangeRatesCache is the class that uses ExchangeRatesProvider and provides the cached exchange rates
- - Both ExchangeRatesService and CurrencyConversionService are using the cache.

- Swagger is configured and can be found at http://localhost:8080/swagger-ui.html
-Additionally, I have implemented two Converters used in the Controllers
- - CurrencyStringToEnumConverter: Gets a String with a currency code and converts it to an enum
- - CurrencyStringToEnumListConverter: Gets a String with comma separated currency codes and converts it to an enum list
- And a GlobalExceptionHandler for some common exceptions
- At last, i have created unit tests for both controllers and for the exchange rate clients.


## Run Details
To run you could use 
> ./run.sh

It will basically build/run exchange-rates app and a docker redis instance.

Ports 8080 and 6379 need to be available


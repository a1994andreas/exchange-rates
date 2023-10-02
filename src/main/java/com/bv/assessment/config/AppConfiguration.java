package com.bv.assessment.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableWebMvc
@Configuration
@EnableCaching
public class AppConfiguration {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		Map<String, RedisCacheConfiguration> cacheNamesConfigurationMap = new HashMap<>();
		cacheNamesConfigurationMap.put("exchangeRates",
				RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60)));
		cacheNamesConfigurationMap.put("connections",
				RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)));

		return new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory), RedisCacheConfiguration.defaultCacheConfig(), cacheNamesConfigurationMap);
	}
}

package com.bv.assessment.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CheckConnectionUtil {

	@Cacheable(value = "connections")
	public Boolean checkConnection(String baseUrl) {
		Boolean success = Boolean.FALSE;
		try {
			URL url = new URL(baseUrl);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			int responseCode = huc.getResponseCode();
			if (responseCode == HttpStatus.OK.value()) {
				success = Boolean.TRUE;
			}
		} catch (Exception e) {

		}
		return success;
	}
}

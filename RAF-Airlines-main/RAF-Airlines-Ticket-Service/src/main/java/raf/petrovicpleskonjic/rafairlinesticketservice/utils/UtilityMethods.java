package raf.petrovicpleskonjic.rafairlinesticketservice.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UtilityMethods {
	
	public static final String USER_SERVICE_URL = "http://localhost:8080/";
	public static final String FLIGHT_SERVICE_URL = "http://localhost:8081/";
	
	public static <T> ResponseEntity<T> sendGet(Class<T> responseType, String url, String token) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

		return response;
	}

	public static <T> ResponseEntity<T> sendPost(Class<T> responseType, String url, String token, Object body) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

		return response;
	}
}

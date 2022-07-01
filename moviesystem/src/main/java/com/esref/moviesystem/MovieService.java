package com.esref.moviesystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class MovieService implements IMovieService {
	
	@Override
	public List<Movie> search(String title) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("authorization", "apikey 4ALL1gH68A4XCNpY9CdBXn:7Hti197hM0O4dmloEo6Q17");
		String url = "https://api.collectapi.com/imdb/imdbSearchByName?query=" + title;
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(url, HttpMethod.GET, requestEntity, String.class);
		String res = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Movie> movies = new ArrayList<Movie>();
		
		try {
			JsonNode node = objectMapper.readTree(res);
			JsonNode resultNode = node.get("result");
			if (resultNode.isArray()) {
				ArrayNode moviesNode = (ArrayNode) resultNode;
				for (int i = 0; i < moviesNode.size(); i++) {
					JsonNode singleMovie = moviesNode.get(i);
					String movieTitle = singleMovie.get("Title").toString();
					String year = singleMovie.get("Year").toString();
					String imdbId = singleMovie.get("imdbID").toString();
					String type = singleMovie.get("Type").toString();
					String poster = singleMovie.get("Poster").toString();
					Movie m = new Movie();
					m.setTitle(movieTitle);
					m.setYear(year);
					m.setImdbID(imdbId);
					m.setType(type);
					m.setPoster(poster);
					movies.add(m);
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return movies;
	}

	@Override
	public Movie findById(String id) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("authorization", "apikey 4ALL1gH68A4XCNpY9CdBXn:7Hti197hM0O4dmloEo6Q17");
		String url = "https://api.collectapi.com/imdb/imdbSearchById?movieId=" + id;
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(url, HttpMethod.GET, requestEntity, String.class);
		String res = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		Movie m = new Movie();
		try {
			JsonNode node = objectMapper.readTree(res);
			JsonNode resultNode = node.get("result");
			String movieTitle = resultNode.get("Title").toString();
			String year = resultNode.get("Year").toString();
			String imdbId = resultNode.get("imdbID").toString();
			String type = resultNode.get("Type").toString();
			String poster = resultNode.get("Poster").toString();
			m.setTitle(movieTitle);
			m.setYear(year);
			m.setImdbID(imdbId);
			m.setType(type);
			m.setPoster(poster);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return m;
	}

}

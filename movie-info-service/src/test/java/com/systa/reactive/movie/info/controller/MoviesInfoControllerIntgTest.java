package com.systa.reactive.movie.info.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.repository.MovieInfoRepository;

import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntgTest {
	
	@Autowired
	MovieInfoRepository movieInfoRepository;
	
	@Autowired
	WebTestClient webTestClient;
	
	static String MOVIES_INFO_URL = "/v1/movieInfos";
	
	@BeforeEach
	void setUp() {
		List<MovieInfo> movies = Arrays.asList(
				new MovieInfo(null, "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo(null, "Superman", "2022", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo("abc", "Heman", "2023", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))
		);
		
		movieInfoRepository.saveAll(movies).blockLast();
				
	}
	
	@AfterEach
	void tearDown() {
		movieInfoRepository.deleteAll().block();
	}

	@Test
	void addMovieInfoTest() {
	
		var movieToSave = new MovieInfo(null, "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		
		webTestClient
			.post()
			.uri(MOVIES_INFO_URL)
			.bodyValue(movieToSave)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody(MovieInfo.class)
			.consumeWith(entityExchangeResult -> {
				
				var movieWhichIsSaved = entityExchangeResult.getResponseBody();
				assert movieWhichIsSaved != null;
				assert movieWhichIsSaved.getId() != null;
				
			});
		
	}
	
	@Test
	void getAllMovieInfosTest() {
	
		webTestClient
			.get()
			.uri(MOVIES_INFO_URL)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBodyList(MovieInfo.class)
			.hasSize(3);
		
	}
	
	@Test
	void getAllMovieInfoByIdTest() {
		
		String idToFetch = "abc";
	
		webTestClient
			.get()
			.uri(MOVIES_INFO_URL+"/{id}", idToFetch)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBody()
			.jsonPath("$.name", "Heman");
		
	}
	
	@Test
	void updateMovieInfoTest() {
	
		var movieToUpdate = new MovieInfo(null, "Batman11", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		String idToFetch = "abc";
		
		webTestClient
			.put()
			.uri(MOVIES_INFO_URL+"/{id}", idToFetch)
			.bodyValue(movieToUpdate)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBody(MovieInfo.class)
			.consumeWith(entityExchangeResult -> {
				
				var movieWhichIsUpdated = entityExchangeResult.getResponseBody();
				assert movieWhichIsUpdated != null;
				assert movieWhichIsUpdated.getId() != null;
				assertEquals("Batman11", movieWhichIsUpdated.getName());
				
			});
		
	}
	
	@Test
	void updateMovieInfoTest_whenIdIsInvalid() {
	
		var movieToUpdate = new MovieInfo(null, "Batman11", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		String idToFetch = "def";
		
		webTestClient
			.put()
			.uri(MOVIES_INFO_URL+"/{id}", idToFetch)
			.bodyValue(movieToUpdate)
			.exchange()
			.expectStatus()
			.isNotFound();
		
	}
	
	@Test
	void deleteMovieInfoTest() {
	
		String idToFetch = "abc";
		
		webTestClient
			.delete()
			.uri(MOVIES_INFO_URL+"/{id}", idToFetch)
			.exchange()
			.expectStatus()
			.isNoContent();
		
		var flux = movieInfoRepository.findAll().log();
		StepVerifier.create(flux)
			.expectNextCount(2)
			.verifyComplete();
		
	}
	

}

package com.systa.reactive.movie.info.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.service.MoviesInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
class MoviesInfoControllerUnitTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@MockBean
	MoviesInfoService movieInfoService;
	
	static String MOVIES_INFO_URL = "/v1/movieInfos";

	@Test
	void getAllMovieInfosTest() {
		
		List<MovieInfo> movies = Arrays.asList(
				new MovieInfo(null, "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo(null, "Superman", "2022", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo("abc", "Heman", "2023", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))
		);
		
		when(movieInfoService.getAllMovieInfos()).thenReturn(Flux.fromIterable(movies));
	
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
		
		var movie = new MovieInfo("abc", "Heman", "2023", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		
		String idToFetch = "abc";
		
		when(movieInfoService.getMovieInfoById(Mockito.anyString())).thenReturn(Mono.just(movie));
	
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
	void addMovieInfoTest() {
	
		var movieToSave = new MovieInfo(null, "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		
		when(movieInfoService.addMovieInfo(Mockito.any())).thenReturn(Mono.just(
				new MovieInfo("mockId", "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))));
		
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
				assertEquals("mockId", movieWhichIsSaved.getId());
				
			});
		
	}
	
	@Test
	void updateMovieInfoTest() {
	
		var movieToUpdate = new MovieInfo(null, "Batman11", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		String idToFetch = "abc";
		
		when(movieInfoService.updateMovieInfo(Mockito.any(), Mockito.anyString())).thenReturn(Mono.just(
				new MovieInfo("abc", "Batman11", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))));
		
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
				assertEquals("abc", movieWhichIsUpdated.getId());
				
			});		
	}
	
	@Test
	void deleteMovieInfoTest() {
	
		String idToFetch = "abc";
		
		when(movieInfoService.deleteMovieInfo(Mockito.anyString())).thenReturn(Mono.empty());
		
		webTestClient
			.delete()
			.uri(MOVIES_INFO_URL+"/{id}", idToFetch)
			.exchange()
			.expectStatus()
			.isNoContent();
		
	}
	
	@Test
	void addMovieInfoTest_whenNameAndYearIsInvalidInRequest() {
	
		var movieToSave = new MovieInfo(null, "", "-1" , Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"));
		
		webTestClient
			.post()
			.uri(MOVIES_INFO_URL)
			.bodyValue(movieToSave)
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody(String.class)
			.consumeWith(entityExchangeResult -> {
				var responseBody = entityExchangeResult.getResponseBody();
				var expectedMessage = "movie.name should not be empty,movie.year should be positive value";
				
				assert responseBody != null;
				assertEquals(expectedMessage, responseBody);
			});
		
	}

}

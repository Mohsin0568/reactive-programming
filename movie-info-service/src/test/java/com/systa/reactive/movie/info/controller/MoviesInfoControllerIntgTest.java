package com.systa.reactive.movie.info.controller;

import static org.junit.jupiter.api.Assertions.fail;

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

}

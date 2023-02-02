package com.systa.reactive.movie.info.repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.systa.reactive.movie.info.domain.MovieInfo;

import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryTest {
	
	@Autowired
	MovieInfoRepository movieInfoRepository;
	
	@BeforeEach
	void setUp() {
		List<MovieInfo> movies = Arrays.asList(
				new MovieInfo(null, "Batman", "2021", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo(null, "Superman", "2022", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30")),
				new MovieInfo(null, "Heman", "2023", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))
		);
		
		movieInfoRepository.saveAll(movies).blockLast();
				
	}
	
	@AfterEach
	void tearDown() {
		movieInfoRepository.deleteAll().block();
	}

	@Test
	void findAll() {
		
		var flux = movieInfoRepository.findAll();
		StepVerifier.create(flux)
			.expectNextCount(3)
			.verifyComplete();
		
	}

}

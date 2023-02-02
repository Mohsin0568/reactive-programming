package com.systa.reactive.movie.info.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
				new MovieInfo("abc", "Heman", "2023", Arrays.asList("abc", "xyz"), LocalDate.parse("2022-05-30"))
		);
		
		movieInfoRepository.saveAll(movies).blockLast();
				
	}
	
	@AfterEach
	void tearDown() {
		movieInfoRepository.deleteAll().block();
	}

	@Test
	void findAll() {
		
		var flux = movieInfoRepository.findAll().log();
		StepVerifier.create(flux)
			.expectNextCount(3)
			.verifyComplete();
		
	}
	
	@Test
	void findById() {
		
		var mono = movieInfoRepository.findById("abc").log();
		StepVerifier.create(mono)
			.assertNext(movieInfo -> {
				assertEquals("Heman", movieInfo.getName());
			})
			.verifyComplete();
		
	}
	
	@Test
	void save() {
		
		var mono = movieInfoRepository.save(
				new MovieInfo(null, "Batman1", "2021", Arrays.asList("abc", "xyz"), 
						LocalDate.parse("2022-05-30"))
				).log();
		
		StepVerifier.create(mono)
			.assertNext(movieInfo -> {
				assertNotNull(movieInfo.getId());
				assertEquals("Batman1", movieInfo.getName());
			})
			.verifyComplete();
		
	}
	
	@Test
	void update() {
		
		var movieInfo = movieInfoRepository.findById("abc").block();
		movieInfo.setYear("2012");
		
		var mono = movieInfoRepository.save(movieInfo).log();
		
		StepVerifier.create(mono)
			.assertNext(movieInfo1 -> {
				assertNotNull(movieInfo1.getId());
				assertEquals("2012", movieInfo1.getYear());
			})
			.verifyComplete();
		
	}
	
	@Test
	void delete() {
		
		movieInfoRepository.deleteById("abc").block();
		var flux = movieInfoRepository.findAll().log();
		StepVerifier.create(flux)
			.expectNextCount(2)
			.verifyComplete();
		
	}

}

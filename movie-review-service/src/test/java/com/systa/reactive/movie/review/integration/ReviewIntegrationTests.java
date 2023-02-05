package com.systa.reactive.movie.review.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.reactive.movie.review.domain.Review;
import com.systa.reactive.movie.review.repository.ReviewRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ReviewIntegrationTests {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	WebTestClient webTestClient;
	
	static String REVIEW_INFO_URL = "/v1/review";
	
	@BeforeEach
	void setUp() {
		var reviewsList = List.of(
                new Review("abc", 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie1", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0));
		
		reviewRepository.saveAll(reviewsList).blockLast();
				
	}
	
	@AfterEach
	void tearDown() {
		reviewRepository.deleteAll().block();
	}

	@Test
	void addReviewTest() {
		var reviewToSave = new Review(null, 1L, "Chakkas Movie", 10.0);
		
		webTestClient
			.post()
			.uri(REVIEW_INFO_URL)
			.bodyValue(reviewToSave)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody(Review.class)
			.consumeWith(entityExchangeResult -> {
				
				var reviewWhichIsSaved = entityExchangeResult.getResponseBody();
				assert reviewWhichIsSaved != null;
				assert reviewWhichIsSaved.getReviewId() != null;
				
			});
	}
	
	@Test
	void getAllMoviesReviewTest() {
	
		webTestClient
			.get()
			.uri(REVIEW_INFO_URL)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBodyList(Review.class)
			.hasSize(3);
		
	}
	
	@Test
	void updateMovieReviewTest() {
	
		var reviewToUpdate = new Review(null, 1L, "Awesome Movie1", 9.0);
		String idToFetch = "abc";
		
		webTestClient
			.put()
			.uri(REVIEW_INFO_URL+"/{id}", idToFetch)
			.bodyValue(reviewToUpdate)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBody(Review.class)
			.consumeWith(entityExchangeResult -> {
				
				var reviewWhichIsUpdated = entityExchangeResult.getResponseBody();
				assert reviewWhichIsUpdated != null;
				assert reviewWhichIsUpdated.getReviewId() != null;
				assertEquals("Awesome Movie1", reviewWhichIsUpdated.getComment());
				
			});
		
	}
	
	@Test
	void deleteMovieReviewTest() {
	
		String idToFetch = "abc";
		
		webTestClient
			.delete()
			.uri(REVIEW_INFO_URL+"/{id}", idToFetch)
			.exchange()
			.expectStatus()
			.isNoContent();
	}

}

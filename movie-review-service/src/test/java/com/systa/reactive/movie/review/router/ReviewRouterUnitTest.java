package com.systa.reactive.movie.review.router;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.reactive.movie.review.domain.Review;
import com.systa.reactive.movie.review.exceptions.GlobalErrorHandler;
import com.systa.reactive.movie.review.handler.ReviewHandler;
import com.systa.reactive.movie.review.repository.ReviewRepository;

import reactor.core.publisher.Mono;


@WebFluxTest
@ContextConfiguration(classes = {ReviewRouter.class, ReviewHandler.class, GlobalErrorHandler.class})
@AutoConfigureWebTestClient
class ReviewRouterUnitTest {
	
	@MockBean
	private ReviewRepository reviewRepository;
	
	@Autowired
	private WebTestClient webTestClient;
	
	static String REVIEW_INFO_URL = "/v1/review";

	@Test
	void addReviewTest() {
		
		var reviewToSave = new Review(null, 1L, "Chakkas Movie", 10.0);
		
		when(reviewRepository.save(Mockito.any())).thenReturn(Mono.just(new Review("testId", 1L, "Chakkas Movie", 10.0)));
		
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
	void addReviewTest_InvalidData() {
		
		var reviewToSave = new Review(null, null, "Chakkas Movie", -10.0);
		
		webTestClient
			.post()
			.uri(REVIEW_INFO_URL)
			.bodyValue(reviewToSave)
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody(String.class)
			.isEqualTo("movieInfoId should not be null,rating value should be a positive value");		
		
	}

}

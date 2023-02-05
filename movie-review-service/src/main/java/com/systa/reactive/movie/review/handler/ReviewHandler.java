/**
 * 
 */
package com.systa.reactive.movie.review.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.systa.reactive.movie.review.domain.Review;
import com.systa.reactive.movie.review.repository.ReviewRepository;

import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@Component
public class ReviewHandler {
	
	@Autowired
	ReviewRepository reviewRepository;

	public Mono<ServerResponse> saveReveiw(ServerRequest request) {
		
		
		return request.bodyToMono(Review.class)
			.flatMap(reviewRepository :: save)
			.flatMap(ServerResponse.status(HttpStatus.CREATED) :: bodyValue);
		
	}

	public Mono<ServerResponse> getAllReviews() {
		var reviewsFlux = reviewRepository.findAll();
		return ServerResponse.ok().body(reviewsFlux, Review.class);
	}

	public Mono<ServerResponse> updateReview(ServerRequest request) {
		String id = request.pathVariable("id");
		
		var existingReview = reviewRepository.findById(id);
		
		return existingReview.flatMap(review -> 
				request.bodyToMono(Review.class).map(reqReview ->
				{
					review.setComment(reqReview.getComment());
					review.setRating(reqReview.getRating());
					return review;
				})
				.flatMap(reviewRepository :: save)
				.flatMap(ServerResponse.ok() :: bodyValue)
				);
	}
}
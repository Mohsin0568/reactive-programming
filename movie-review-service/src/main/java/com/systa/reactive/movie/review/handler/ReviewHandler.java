/**
 * 
 */
package com.systa.reactive.movie.review.handler;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.systa.reactive.movie.review.domain.Review;
import com.systa.reactive.movie.review.exceptions.ReviewDataException;
import com.systa.reactive.movie.review.repository.ReviewRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@Component
public class ReviewHandler {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	Validator validator;

	public Mono<ServerResponse> saveReveiw(ServerRequest request) {
		
		
		return request.bodyToMono(Review.class)
			.doOnNext(this :: validate)
			.flatMap(reviewRepository :: save)
			.flatMap(ServerResponse.status(HttpStatus.CREATED) :: bodyValue);
		
	}
	
	private void validate(Review review) {
		
		var setOfConstraintViolations = validator.validate(review);
		if(!setOfConstraintViolations.isEmpty()) {
			var errorMessage = setOfConstraintViolations
			.stream()
			.map(ConstraintViolation :: getMessage)
			.sorted()
			.collect(Collectors.joining(","));
			
			throw new ReviewDataException(errorMessage);
		}
	}

	public Mono<ServerResponse> getAllReviews(ServerRequest request) {
		
		var movieInfoId = request.queryParam("movieInfoId");
		Flux<Review> reviewsFlux = null;
		if(movieInfoId.isPresent()) {
			reviewsFlux = reviewRepository.findReviewsByMovieInfoId(Long.valueOf(movieInfoId.get()));			
		}
		else {		
			reviewsFlux = reviewRepository.findAll();
		}
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

	public Mono<ServerResponse> deleteReview(ServerRequest request) {
	
		String id = request.pathVariable("id");		
		return reviewRepository.deleteById(id).then(ServerResponse.noContent().build());
	}
}

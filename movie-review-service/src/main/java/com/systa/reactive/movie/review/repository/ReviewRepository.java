/**
 * 
 */
package com.systa.reactive.movie.review.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.systa.reactive.movie.review.domain.Review;

import reactor.core.publisher.Flux;

/**
 * @author mohsin
 *
 */

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Review, String>{
	
	public Flux<Review> findReviewsByMovieInfoId(Long id);

}

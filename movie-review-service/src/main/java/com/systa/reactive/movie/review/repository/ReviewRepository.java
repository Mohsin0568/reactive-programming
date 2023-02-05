/**
 * 
 */
package com.systa.reactive.movie.review.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.systa.reactive.movie.review.domain.Review;

/**
 * @author mohsin
 *
 */

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Review, String>{

}

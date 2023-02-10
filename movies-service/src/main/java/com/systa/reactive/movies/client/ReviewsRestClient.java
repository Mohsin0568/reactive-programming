/**
 * 
 */
package com.systa.reactive.movies.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.systa.reactive.movies.domain.Review;

import reactor.core.publisher.Flux;

/**
 * @author mohsin
 *
 */

@Component
public class ReviewsRestClient {
	
	@Autowired
	WebClient webClient;
	
	@Value("${restClient.reviewUrl}")
	private String reviewUrl;
	
	// movieInfoId
	
	public Flux<Review> retrieveReviewByMovieId(String movieId){
		
		var uri = UriComponentsBuilder.fromHttpUrl(reviewUrl)
			.queryParam("movieInfoId", movieId)
			.buildAndExpand().toUriString();
		
		return webClient
			.get()
			.uri(uri)
			.retrieve()
			.bodyToFlux(Review.class)
			.log();
		
	}

}

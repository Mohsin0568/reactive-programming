/**
 * 
 */
package com.systa.reactive.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systa.reactive.movies.client.MoviesInfoRestClient;
import com.systa.reactive.movies.client.ReviewsRestClient;
import com.systa.reactive.movies.domain.Movie;

import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@RestController
@RequestMapping("/v1/movies")
public class MoviesController {
	
	@Autowired
	MoviesInfoRestClient moviesInfoRestClient;
	
	@Autowired
	ReviewsRestClient reviewsRestClient;

	@GetMapping("/{id}")
	public Mono<Movie> getMovieById(@PathVariable("id") String movieId){
		
		return moviesInfoRestClient.fetchMovieInfoById(movieId)
			.flatMap(movieInfo -> {
				
				var reviewListMono = reviewsRestClient.retrieveReviewByMovieId(movieId).collectList();
				
				return reviewListMono.map(reviews -> new Movie(movieInfo, reviews));
				
			});		
	}
}

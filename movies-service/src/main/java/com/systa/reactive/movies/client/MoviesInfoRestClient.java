/**
 * 
 */
package com.systa.reactive.movies.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.systa.reactive.movies.domain.MovieInfo;

import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@Component
public class MoviesInfoRestClient {

	@Autowired
	WebClient webClient;
	
	@Value("${restClient.moviesInfoUrl}")
	private String movieInfoUrl;
	
	public Mono<MovieInfo> fetchMovieInfoById(String movieId){
		
		var url = movieInfoUrl.concat("/{id}");
		
		return webClient
			.get()
			.uri(url, movieId)
			.retrieve()
			.bodyToMono(MovieInfo.class)
			.log();
		
	}
}

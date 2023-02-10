/**
 * 
 */
package com.systa.reactive.movies.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.systa.reactive.movies.domain.MovieInfo;
import com.systa.reactive.movies.exceptions.MoviesInfoClientException;

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
			.onStatus(HttpStatus :: is4xxClientError, clientResponse -> {
				
				if(clientResponse.statusCode().value() == 404) {
					
					return Mono.error(
							new MoviesInfoClientException
							("MovieInfo is not available for id " + movieId, clientResponse.statusCode().value()));
				}
				
				return clientResponse.bodyToMono(String.class)
						.flatMap(responseBody -> {
							return Mono.error(
									new MoviesInfoClientException(responseBody, clientResponse.statusCode().value())
								);
						});
			})
			.bodyToMono(MovieInfo.class)
			.log();
		
	}
}

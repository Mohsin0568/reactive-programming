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
import com.systa.reactive.movies.exceptions.MoviesInfoServerException;

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
		
//		var retry = Retry.fixedDelay(3, Duration.ofSeconds(1))
//			.filter(e -> e instanceof MoviesInfoServerException) // retry will happen only if exception is of type MoviesInfoServerException, there is no point of retrying if the exception is of 404
//			.onRetryExhaustedThrow((retryBackOffSpec, retrySignal) -> { // this line will propagate same exception thrown by the server.
//				return Exceptions.propagate(retrySignal.failure());
//			});
		
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
			.onStatus(HttpStatus :: is5xxServerError, clientResponse -> {
				
				return clientResponse.bodyToMono(String.class)
						.flatMap(responseBody -> {
							return Mono.error(
									new MoviesInfoServerException(responseBody)
								);
						});
			})
			.bodyToMono(MovieInfo.class)
			.retryWhen(RestUtil.getRetryForMovieInfoService())
			.log();
		
	}
}

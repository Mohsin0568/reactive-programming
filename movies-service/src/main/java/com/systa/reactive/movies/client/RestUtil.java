/**
 * 
 */
package com.systa.reactive.movies.client;

import java.time.Duration;

import com.systa.reactive.movies.exceptions.MoviesInfoServerException;

import reactor.core.Exceptions;
import reactor.util.retry.Retry;

/**
 * @author mohsin
 *
 */
public class RestUtil {
	
	public static Retry getRetryForMovieInfoService() {
		
		return Retry.fixedDelay(3, Duration.ofSeconds(1))
				.filter(e -> e instanceof MoviesInfoServerException) // retry will happen only if exception is of type MoviesInfoServerException, there is no point of retrying if the exception is of 404
				.onRetryExhaustedThrow((retryBackOffSpec, retrySignal) -> { // this line will propagate same exception thrown by the server.
					return Exceptions.propagate(retrySignal.failure());
				});
	}

}

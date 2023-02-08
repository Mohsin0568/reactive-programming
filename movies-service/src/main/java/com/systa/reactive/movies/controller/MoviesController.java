/**
 * 
 */
package com.systa.reactive.movies.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systa.reactive.movies.domain.Movie;

import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@RestController
@RequestMapping("/v1/movies")
public class MoviesController {

	@GetMapping("{/id}")
	public Mono<Movie> getMovieById(){
		
		return null;
		
	}
}

/**
 * 
 */
package com.systa.reactive.movie.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.service.MoviesInfoService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@RestController
@RequestMapping("/v1/movieInfos")
@Slf4j
public class MoviesInfoController {
	
	@Autowired
	MoviesInfoService movieInfoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
		
		log.info("Request received for moviesInfo");
		return movieInfoService.addMovieInfo(movieInfo).log();
		
	}
}

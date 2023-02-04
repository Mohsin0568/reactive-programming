/**
 * 
 */
package com.systa.reactive.movie.info.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.service.MoviesInfoService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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
	public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo){
		
		log.info("Request received for moviesInfo");
		return movieInfoService.addMovieInfo(movieInfo).log();
		
	}
	
	@GetMapping
	public Flux<MovieInfo> getAllMovieInfos(){
		log.info("Request received to get all moviesInfos");
		return movieInfoService.getAllMovieInfos().log();
	}
	
	
	@GetMapping("/{id}")
	public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){
		log.info("Request received to get moviesInfo with id {}", id);
		return movieInfoService.getMovieInfoById(id).log();
	}
	
	@PutMapping("/{id}")
	public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo movieInfo, 
				@PathVariable String id){
		log.info("Request received to update moviesInfo with id {}", id);
		return movieInfoService.updateMovieInfo(movieInfo, id).log();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteMovieInfo(@PathVariable String id){
		log.info("Request received to delete moviesInfo with id {}", id);
		return movieInfoService.deleteMovieInfo(id).log();
	}
	
}

/**
 * 
 */
package com.systa.reactive.movie.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.repository.MovieInfoRepository;

import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@Service
public class MoviesInfoService {

	@Autowired
	MovieInfoRepository movieInfoRepository;

	public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
		
		return movieInfoRepository.save(movieInfo);
		
	}
}

/**
 * 
 */
package com.systa.reactive.movie.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.reactive.movie.info.domain.MovieInfo;
import com.systa.reactive.movie.info.repository.MovieInfoRepository;

import reactor.core.publisher.Flux;
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

	public Flux<MovieInfo> getAllMovieInfos() {
		return movieInfoRepository.findAll();
	}

	public Mono<MovieInfo> getMovieInfoById(String id) {
		return movieInfoRepository.findById(id);
	}

	public Mono<MovieInfo> updateMovieInfo(MovieInfo movieInfo, String id) {
		
		return movieInfoRepository.findById(id)
			.flatMap(fetchedMovieInfo -> {
				fetchedMovieInfo.setName(movieInfo.getName());
				fetchedMovieInfo.setCast(movieInfo.getCast());
				fetchedMovieInfo.setYear(movieInfo.getYear());
				fetchedMovieInfo.setReleaseDate(movieInfo.getReleaseDate());
				
				return movieInfoRepository.save(fetchedMovieInfo);
			});
		
	}

	public Mono<Void> deleteMovieInfo(String id) {
		
		return movieInfoRepository.deleteById(id);
	}
}

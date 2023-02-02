package com.systa.reactive.movie.info.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.systa.reactive.movie.info.domain.MovieInfo;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String>{

}

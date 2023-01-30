/**
 * 
 */
package com.systa.reactive.movie.info.service.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@RestController
public class FluxMonoController {

	
	@GetMapping("/flux")
	public Flux<Integer> getFlux(){
		return Flux.just(1, 2, 3, 4).log();
	}
	
	@GetMapping("/mono")
	public Mono<String> getMono(){
		return Mono.just("This is first mono").log();
	}
	
	@GetMapping(value = "/streams", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Long> stream(){
		return Flux.interval(Duration.ofSeconds(1)).log();
	}
}

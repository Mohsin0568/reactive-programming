/**
 * 
 */
package com.systa.reactive.movie.info.service.controller;

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
}

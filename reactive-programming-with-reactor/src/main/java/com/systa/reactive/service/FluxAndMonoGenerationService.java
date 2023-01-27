package com.systa.reactive.service;

import java.util.Arrays;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class FluxAndMonoGenerationService {
	
	public Flux<String> namesFlux(){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John")).log();
	}
	
	public Flux<String> namesFluxMap(){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John")).log().map(String :: toUpperCase);
	}
	
	public Mono<String> nameMono(){
		return Mono.just("Alex").log();
	}

	public static void main(String[] args) {
		
		FluxAndMonoGenerationService service = new FluxAndMonoGenerationService();
		service.namesFlux().subscribe(name -> System.out.println("Flux Name: " + name));
		
		service.nameMono().subscribe(name -> System.out.println("Mono Name: " + name));
	}

}

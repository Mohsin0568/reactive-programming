package com.systa.reactive.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class FluxAndMonoGenerationService {
	
	public Flux<String> namesFlux(){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John")).log();
	}
	
	public Flux<String> namesFluxMap(){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John")).log().map(String :: toUpperCase);
	}
	
	public Flux<String> namesFluxImmutable(){
		var flux = Flux.fromIterable(Arrays.asList("Alex", "Smith", "John"));
		flux.map(String :: toUpperCase); // this will not change to original flux, a new flux will be created and returned by map function.
		return flux;
		
	}
	
	public Flux<String> namesFluxFilterAndMap(int strLength){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))
				.log()
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength)
				.map(s -> s.length() + "-" + s); // adds size of the string to the original string and creates and returns flux.
	}
	
	public Flux<String> namesFluxflatMap(int strLength){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))
				.log()
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength)
				.flatMap(s -> toStringArray(s));
				
	}
	
	public Flux<String> namesFluxflatMapAsync(int strLength){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))				
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength)
				.flatMap(s -> toStringArrayWithDelay(s))
				.log();
				
	}
	
	public Flux<String> toStringArray(String name){
		var array = name.split("");
		return Flux.fromArray(array);
	}
	
	public Flux<String> toStringArrayWithDelay(String name){
		var array = name.split("");
		int duration = new Random().nextInt(1000);
		return Flux.fromArray(array).delayElements(Duration.ofMillis(duration));
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

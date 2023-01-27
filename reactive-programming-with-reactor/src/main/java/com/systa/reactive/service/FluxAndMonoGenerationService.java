package com.systa.reactive.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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
				.flatMap(s -> toStringArrayWithDelay(s)) // flatMap doesn't maintains the order of the elements while processing elements in asynchronous fashion.
				.log();
				
	}
	
	public Flux<String> namesFluxConcatMap(int strLength){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))				
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength)
				.concatMap(s -> toStringArrayWithDelay(s)) // concat map maintains the order of the elements while processing elements in asynchronous fashion.
				.log();
				
	}
	
	public Mono<String> nameMono(){
		return Mono.just("Alex").log();
	}
	
	public Mono<List<String>> nameMonoFlatMap(){
		
		return Mono.just("Alex")
			.map(String :: toUpperCase)
			.flatMap(this :: convertStringToMonoList)
			.log();
	}
	
	public Flux<String> nameMonoFlatMapMany(){
		
		return Mono.just("Alex")
			.map(String :: toUpperCase)
			.flatMapMany(this :: toStringArray)
			.log();
	}
	
	public Flux<String> namesFluxTransform(int strLength){
		
		Function<Flux<String>, Flux<String>> transformFunction = flux -> flux
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength);
		
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))
				.transform(transformFunction)
				.flatMap(s -> toStringArray(s))
				.defaultIfEmpty("default")
				.log();
				
	}
	
	public Flux<String> namesFluxSwitchIfEmpty(int strLength){
		
		Function<Flux<String>, Flux<String>> transformFunction = flux -> flux
				.map(String :: toUpperCase)
				.filter(s -> s.length() > strLength)
				.flatMap(s -> toStringArray(s));
		
		var defaultFlux = Flux.just("default").transform(transformFunction);
		
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John", "Joe"))
				.transform(transformFunction)
				.switchIfEmpty(defaultFlux)
				.log();
				
	}
	
	public Flux<String> exploreConcat(){
		var abcFlux = Flux.just("A", "B", "C");
		var defFlux = Flux.just("D", "E", "F");
		return Flux.concat(abcFlux, defFlux).log();
	}
	
	public Flux<String> exploreConcatWith(){
		var abcFlux = Flux.just("A", "B", "C");
		var defFlux = Flux.just("D", "E", "F");
		return abcFlux.concatWith(defFlux).log();
	}
	
	public Flux<String> exploreConcatWithMono(){
		var monoA = Mono.just("A");
		var monoB = Mono.just("B");
		return monoA.concatWith(monoB).log();
	}
	
	public Flux<String> exploreMerge(){
		var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100)); // this will process alternate elements from both flux
		var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
		return Flux.merge(abcFlux, defFlux).log();
	}
	
	public Flux<String> exploreMergeWith(){
		var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
		var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
		return abcFlux.mergeWith(defFlux).log();
	}
	
	public Flux<String> exploreMergeWithMono(){
		var monoA = Mono.just("A");
		var monoB = Mono.just("B");
		return monoA.mergeWith(monoB).log();
	}
	
	public Flux<String> exploreMergeSequential(){
		var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(1000)); // this will process alternate elements from both flux
		var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(1250));
		return Flux.mergeSequential(abcFlux, defFlux).log();
	}
	
	public Mono<List<String>> convertStringToMonoList(String s){
		var splitStringArray = s.split("");
		var stringsList = Arrays.asList(splitStringArray);
		return Mono.just(stringsList);
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

	public static void main(String[] args) {
		
		FluxAndMonoGenerationService service = new FluxAndMonoGenerationService();
		service.namesFlux().subscribe(name -> System.out.println("Flux Name: " + name));
		
		service.nameMono().subscribe(name -> System.out.println("Mono Name: " + name));
	}

}

package com.systa.reactive;

import java.util.Arrays;

import reactor.core.publisher.Flux;


public class FluxAndMonoGenerationService {
	
	public Flux<String> namesFlux(){
		return Flux.fromIterable(Arrays.asList("Alex", "Smith", "John"));
	}

	public static void main(String[] args) {
		
		FluxAndMonoGenerationService service = new FluxAndMonoGenerationService();
		service.namesFlux().subscribe(System.out :: println);

	}

}

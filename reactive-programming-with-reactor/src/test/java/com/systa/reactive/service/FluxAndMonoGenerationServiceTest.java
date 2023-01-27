package com.systa.reactive.service;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class FluxAndMonoGenerationServiceTest {
	
	FluxAndMonoGenerationService fluxAndMonoService = new FluxAndMonoGenerationService();

	@Test
	void namesFluxTest() {
		
		var namesFlux = fluxAndMonoService.namesFlux();
		
		StepVerifier.create(namesFlux)
			.expectNext("Alex", "Smith", "John")
			.verifyComplete();		
	}
	
	@Test
	void namesFluxMapTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxMap();
		
		StepVerifier.create(namesFlux)
			.expectNext("ALEX", "SMITH", "JOHN")
			.verifyComplete();		
	}
	
	@Test
	void namesFluxImmutableTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxImmutable();
		
		StepVerifier.create(namesFlux)
			.expectNext("Alex", "Smith", "John")
			.verifyComplete();		
	}
	
	@Test
	void namesFluxFilterAndMapTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxFilterAndMap(3);
		
		StepVerifier.create(namesFlux)
			.expectNext("4-ALEX", "5-SMITH", "4-JOHN")
			.verifyComplete();
		
		namesFlux = fluxAndMonoService.namesFluxFilterAndMap(4);
		
		StepVerifier.create(namesFlux)
			.expectNext("5-SMITH")
			.verifyComplete();	
	}
	
	@Test
	void namesFluxFlatMapTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxflatMap(3);
		
		StepVerifier.create(namesFlux)
			.expectNext("A", "L", "E", "X", "S" ,"M", "I", "T", "H", "J", "O", "H", "N")
			.verifyComplete();
		
		namesFlux = fluxAndMonoService.namesFluxflatMap(4);
		
		StepVerifier.create(namesFlux)
			.expectNext("S" ,"M", "I", "T", "H")
			.verifyComplete();	
	}
	
	@Test
	void namesFluxFlatMapAsyncTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxflatMapAsync(3);
		
		StepVerifier.create(namesFlux)
			.expectNextCount(13)
			.verifyComplete();	
	}

}

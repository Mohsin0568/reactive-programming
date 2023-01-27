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

}

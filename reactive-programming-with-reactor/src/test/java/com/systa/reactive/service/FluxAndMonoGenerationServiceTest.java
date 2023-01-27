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

}

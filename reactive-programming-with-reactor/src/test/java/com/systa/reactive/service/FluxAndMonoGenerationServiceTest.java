package com.systa.reactive.service;

import java.util.Arrays;

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
	
	@Test
	void namesFluxConcatMapTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxConcatMap(3);
		
		StepVerifier.create(namesFlux)
			.expectNext("A", "L", "E", "X", "S" ,"M", "I", "T", "H", "J", "O", "H", "N")
			.verifyComplete();
	}
	
	@Test
	void nameMonoFlatMapTest() {
		
		var namesFlux = fluxAndMonoService.nameMonoFlatMap();
		
		StepVerifier.create(namesFlux)
			.expectNext(Arrays.asList("A", "L", "E", "X"))
			.verifyComplete();
	}
	
	@Test
	void nameMonoFlatMapManyTest() {
		
		var namesFlux = fluxAndMonoService.nameMonoFlatMapMany();
		
		StepVerifier.create(namesFlux)
			.expectNext("A", "L", "E", "X")
			.verifyComplete();
	}
	
	@Test
	void namesFluxTransformTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxTransform(3);
		
		StepVerifier.create(namesFlux)
			.expectNext("A", "L", "E", "X", "S" ,"M", "I", "T", "H", "J", "O", "H", "N")
			.verifyComplete();
		
		namesFlux = fluxAndMonoService.namesFluxTransform(4);
		
		StepVerifier.create(namesFlux)
			.expectNext("S" ,"M", "I", "T", "H")
			.verifyComplete();
		
		namesFlux = fluxAndMonoService.namesFluxTransform(6);
		
		StepVerifier.create(namesFlux)
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	void namesFluxSwitchIfEmptyTest() {
		
		var namesFlux = fluxAndMonoService.namesFluxSwitchIfEmpty(6);
		
		StepVerifier.create(namesFlux)
			.expectNext("D", "E", "F", "A", "U", "L", "T")
			.verifyComplete();
	}
	
	@Test
	void exploreConcatTests() {
		var testFlux = fluxAndMonoService.exploreConcat();
		StepVerifier.create(testFlux)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
		
		testFlux = fluxAndMonoService.exploreConcatWith();
		StepVerifier.create(testFlux)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
		
		testFlux = fluxAndMonoService.exploreConcatWithMono();
		StepVerifier.create(testFlux)
			.expectNext("A", "B")
			.verifyComplete();
	}
	
	@Test
	void exploreMergeTests() {
		var testFlux = fluxAndMonoService.exploreMerge();
		StepVerifier.create(testFlux)
			.expectNext("A", "D", "B", "E", "C", "F")
			.verifyComplete();
		
		testFlux = fluxAndMonoService.exploreMergeWith();
		StepVerifier.create(testFlux)
			.expectNext("A", "D", "B", "E", "C", "F")
			.verifyComplete();
		
		testFlux = fluxAndMonoService.exploreMergeWithMono();
		StepVerifier.create(testFlux)
			.expectNext("A", "B")
			.verifyComplete();
	}
	
	@Test
	void exploreMergeSequentialTests() {
		var testFlux = fluxAndMonoService.exploreMergeSequential();
		StepVerifier.create(testFlux)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();		
	}

}

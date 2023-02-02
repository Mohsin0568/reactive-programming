package com.systa.reactive.movie.info.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.reactive.movie.info.controller.FluxMonoController;

import reactor.test.StepVerifier;


@WebFluxTest(controllers = FluxMonoController.class)
@AutoConfigureWebTestClient
class FluxMonoControllerTest {
	
	@Autowired
	WebTestClient webTestClient;

	@Test
	void getFluxTest() {
		
		webTestClient
			.get()
			.uri("/flux")
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBodyList(Integer.class)
			.hasSize(4);
		
	}
	
	@Test
	void getFluxTest_approach2() {
		
		var flux = webTestClient
			.get()
			.uri("/flux")
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.returnResult(Integer.class)
			.getResponseBody();
		
		StepVerifier.create(flux)
			.expectNext(1, 2, 3, 4)
			.verifyComplete();
		
	}
	
	@Test
	void getMonoTest() {
		
		var flux = webTestClient
			.get()
			.uri("/mono")
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.returnResult(String.class)
			.getResponseBody();
		
		StepVerifier.create(flux)
			.expectNext("This is first mono")
			.verifyComplete();
		
	}
	
	@Test
	void streamTest() {
		
		var flux = webTestClient
			.get()
			.uri("/streams")
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.returnResult(Long.class)
			.getResponseBody();
		
		StepVerifier.create(flux)
			.expectNext(0l,1l, 2l, 3l, 4l)
			.thenCancel()
			.verify();
		
	}

}

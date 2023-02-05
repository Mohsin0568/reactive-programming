/**
 * 
 */
package com.systa.reactive.movie.review.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author mohsin
 *
 */

@Configuration
public class ReviewRouter {

	@Bean
	public RouterFunction<ServerResponse> reviewRoutes(){
		return route()
				.GET("/testEndpoint", (request -> ServerResponse.ok().bodyValue("test endpoint")))
				.build();
	}
}

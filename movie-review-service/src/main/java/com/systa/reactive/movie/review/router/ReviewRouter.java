/**
 * 
 */
package com.systa.reactive.movie.review.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.systa.reactive.movie.review.handler.ReviewHandler;

/**
 * @author mohsin
 *
 */

@Configuration
public class ReviewRouter {

	@Bean
	public RouterFunction<ServerResponse> reviewRoutes(ReviewHandler handler){
		return RouterFunctions.route()
				.GET("/testEndpoint", (request -> ServerResponse.ok().bodyValue("test endpoint")))
				.POST("/v1/review", handler::saveReveiw)
				//.POST("/v1/review", request -> handler.saveReveiw(request))
				.GET("/v1/review", request -> handler.getAllReviews())
				.build();
	}
}

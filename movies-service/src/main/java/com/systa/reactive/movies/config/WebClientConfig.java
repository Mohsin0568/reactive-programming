/**
 * 
 */
package com.systa.reactive.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author mohsin
 *
 */

@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.build();
	}

}

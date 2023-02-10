/**
 * 
 */
package com.systa.reactive.movies.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author mohsin
 *
 */

@Data
@AllArgsConstructor
public class Movie {
	
	private MovieInfo movieInfo;
	private List<Review> reviws;

}

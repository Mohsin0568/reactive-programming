/**
 * 
 */
package com.systa.reactive.movies.domain;

import java.util.List;

import lombok.Data;

/**
 * @author mohsin
 *
 */

@Data
public class Movie {
	
	private MovieInfo movieInfo;
	private List<Review> reviws;

}

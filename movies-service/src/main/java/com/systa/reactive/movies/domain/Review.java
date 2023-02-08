/**
 * 
 */
package com.systa.reactive.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mohsin
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private String reviewId;
    
    private Long movieInfoId;
    private String comment;
    
    private Double rating;
}
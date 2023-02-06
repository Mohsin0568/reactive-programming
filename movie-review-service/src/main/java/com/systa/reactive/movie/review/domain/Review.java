/**
 * 
 */
package com.systa.reactive.movie.review.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document
public class Review {

    @Id
    private String reviewId;
    
    @NotNull(message = "movieInfoId should not be null")
    private Long movieInfoId;
    private String comment;
    
    @Min(value = 0L, message = "rating value should be a positive value")
    private Double rating;
}
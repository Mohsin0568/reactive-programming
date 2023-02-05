/**
 * 
 */
package com.systa.reactive.movie.review.exceptions;

/**
 * @author mohsin
 *
 */

public class ReviewDataException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
    public ReviewDataException(String s) {
        super(s);
        this.message=s;
    }
}
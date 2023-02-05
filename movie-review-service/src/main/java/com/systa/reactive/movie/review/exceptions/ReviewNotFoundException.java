/**
 * 
 */
package com.systa.reactive.movie.review.exceptions;

/**
 * @author mohsin
 *
 */

public class ReviewNotFoundException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
    private Throwable ex;

    public ReviewNotFoundException( String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public ReviewNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
/**
 * 
 */
package com.systa.reactive.movies.exceptions;

/**
 * @author mohsin
 *
 */
public class MoviesInfoServerException extends RuntimeException{
    private String message;


    public MoviesInfoServerException(String message) {
        super(message);
        this.message = message;
    }
}

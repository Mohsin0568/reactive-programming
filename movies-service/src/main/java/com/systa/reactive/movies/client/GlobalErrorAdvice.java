/**
 * 
 */
package com.systa.reactive.movies.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.systa.reactive.movies.exceptions.MoviesInfoClientException;
import com.systa.reactive.movies.exceptions.MoviesInfoServerException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mohsin
 *
 */

@ControllerAdvice
@Slf4j
public class GlobalErrorAdvice {
	
	@ExceptionHandler(MoviesInfoClientException.class)
    public ResponseEntity<String> handleClientException(MoviesInfoClientException ex){
        log.error("Exception caught in handleClientException :  {} " ,ex.getMessage(),  ex);
        log.info("Status value is : {}", ex.getStatusCode());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatusCode())).body(ex.getMessage());
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericException(MoviesInfoServerException ex){
        log.error("Exception caught in handleGenericException :  {} " ,ex.getMessage(),  ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}

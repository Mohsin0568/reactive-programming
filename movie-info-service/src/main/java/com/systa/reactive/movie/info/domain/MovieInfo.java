package com.systa.reactive.movie.info.domain;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document
public class MovieInfo {
	
	@Id
	private String id;
	
	@NotEmpty(message = "movie.name should not be empty")
	private String name;
	
	@NotNull
	@Positive(message = "movie.year should be positive value")
	private String year;
	
	private List<String> cast;
	private LocalDate releaseDate;

}

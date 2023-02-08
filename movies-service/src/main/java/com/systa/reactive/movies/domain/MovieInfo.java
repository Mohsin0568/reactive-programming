package com.systa.reactive.movies.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieInfo {
	
	private String id;
	
	private String name;
	
	private String year;
	
	private List<String> cast;
	private LocalDate releaseDate;

}

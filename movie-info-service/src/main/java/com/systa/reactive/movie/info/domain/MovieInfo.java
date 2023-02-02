package com.systa.reactive.movie.info.domain;

import java.time.LocalDate;
import java.util.List;

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
	private String name;
	private String year;
	private List<String> cast;
	private LocalDate releaseDate;

}

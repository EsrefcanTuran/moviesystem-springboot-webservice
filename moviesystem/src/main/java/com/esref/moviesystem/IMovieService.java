package com.esref.moviesystem;

import java.util.List;

public interface IMovieService {
	
	public List<Movie> search(String title);
	
	public Movie findById(String id);

}

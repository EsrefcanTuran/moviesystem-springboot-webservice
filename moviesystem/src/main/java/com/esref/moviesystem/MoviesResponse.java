package com.esref.moviesystem;

import java.util.List;

public class MoviesResponse {
	
	private boolean success;
	private List<Movie> result;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Movie> getResult() {
		return result;
	}
	public void setResult(List<Movie> result) {
		this.result = result;
	}
	
	

}

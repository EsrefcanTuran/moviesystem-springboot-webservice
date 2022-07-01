package com.esref.moviesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
	
	@Autowired
	private IMovieService service;
	
	@RequestMapping(path = "/movies/search", method = RequestMethod.GET)
	public List<Movie> search(@RequestParam(name = "movie_name") String name) {
		return this.service.search(name);
	}
	
	@PostMapping("/movies/saveToList/{id}")
	public boolean addToList(@PathVariable(name = "id") String id) {
		Movie m = this.service.findById(id);
		String fileLine = m.getImdbID() + "," + m.getTitle() + "," + m.getYear() + "," + m.getType() + "," + m.getPoster();
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(new File("list.txt")));
			wr.write(fileLine);
			wr.newLine();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@PostMapping("/movies/detail/{id}")
	public Movie detail(@PathVariable(name = "id") String id) throws IOException {
		String idCheck = "\"" + id + "\"";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("list.txt"));
			String fileLine = br.readLine();
			while (fileLine != null) {
				String[] parts = fileLine.split(",");
				if (parts[0].equals(idCheck)) {
					Movie m = new Movie();
					m.setImdbID(parts[0]);
					m.setTitle(parts[1]);
					m.setYear(parts[2]);
					m.setType(parts[3]);
					m.setPoster(parts[4]);
					br.close();
					return m;
				} else {
					fileLine = br.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.service.findById(id);
	}

}

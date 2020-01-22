package cinema.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	MovieRepository repoMovie;
	
	@GetMapping
	@ResponseBody
	public List<Movie> movies(){
		return repoMovie.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<Movie> movie(@PathVariable("id") int id){
		return repoMovie.findById(id);
	}
	
	@GetMapping("/partTitle")
	@ResponseBody
	public Set<Movie> movie(@RequestParam("pt") String partialname){
		return repoMovie.findByTitleContaining(partialname);
	}
	
	@GetMapping("/byTitleAndYear")
	@ResponseBody
	public Set<Movie> movie(@RequestParam("t") String partialname,@RequestParam("y") int year){
		return repoMovie.findByTitleAndYear(partialname, year);
	}
	
	@PostMapping
	@ResponseBody
	public Movie addMovie(@RequestBody Movie m) {
		var saved = repoMovie.save(m);
		repoMovie.flush();
		return saved;
	}

	@PutMapping("/modify")
	@ResponseBody
	public Optional<Movie> modifyMovie(@RequestBody Movie movie) {
		var optMovie = repoMovie.findById(movie.getId());
		//TODO : anywhere else (normalement pas d'algo dans un controller)
			optMovie.ifPresent(m -> {
				m.setTitle(movie.getTitle());
				m.setYear(movie.getYear());
				m.setDuration(movie.getDuration());
				m.setDirector(movie.getDirector());
				repoMovie.flush();	
			});
		return optMovie;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public Optional<Movie> deleteMovie(@PathVariable("id") int id){
		Optional<Movie> movie = repoMovie.findById(id);
		if (movie.isPresent()) {
			repoMovie.deleteById(id);
			repoMovie.flush();
		}
		return movie;
	}
	
}

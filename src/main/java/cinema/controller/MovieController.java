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
import cinema.service.IMovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	IMovieService movieService;
	
	@GetMapping
	@ResponseBody
	public List<Movie> allMovies(){
		return movieService.getAllMovies();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<Movie> movieById(@PathVariable("id") int id){
		return movieService.getMovieById(id);
	}
	
	@GetMapping("/partTitle")
	@ResponseBody
	public Set<Movie> movieByPartialTitle(@RequestParam("pt") String partialtitle){
		return movieService.getMovieByPartialTitle(partialtitle);
	}
	
	@GetMapping("/byTitleAndYear")
	@ResponseBody
	public Set<Movie> movieByTitleAndYear(@RequestParam("t") String title,@RequestParam("y") int year){
		return movieService.getMovieByTitleAndYear(title, year);
	}
	
	@GetMapping("/byDirector")
	@ResponseBody
	public Set<Movie> movieByDirector(@RequestParam("d") String director){
		return movieService.getMovieByDirector(director);
	}
	
	@GetMapping("/byActor")
	@ResponseBody
	public Set<Movie> movieByActor(@RequestParam("a") String actor){
		return movieService.getMovieByActor(actor);
	}
	
	@GetMapping("/byActorOrDirector")
	@ResponseBody
	public Set<Movie> movieByActorOrDirector(@RequestParam("a") String actor,@RequestParam("d") String director){
		return movieService.getMovieByActorOrDirector(actor,director);
	}
	
//	@PostMapping
//	@ResponseBody
//	public Movie addMovie(@RequestBody Movie m) {
//		var saved = repoMovie.save(m);
//		repoMovie.flush();
//		return saved;
//	}
//	
//	@PutMapping("/addActor")
//	@ResponseBody
//	public Optional<Movie> addActor(@RequestParam("m") int id_movie, @RequestParam("p") int id_person){
//		//TODO : anywhere else (normalement pas d'algo dans un controller)
//		var actorOpt = repoPerson.findById(id_person);
//		var movieOpt = repoMovie.findById(id_movie);
//		if (actorOpt.isPresent() && movieOpt.isPresent()) {
//			movieOpt.get().getActors().add(actorOpt.get());
//			repoMovie.flush();
//		}
//		return movieOpt;
//	}
//	
//	@PutMapping("/addDirector")
//	@ResponseBody
//	public Optional<Movie> addDirector(@RequestParam("m") int id_movie, @RequestParam("p") int id_person){
//		//TODO : anywhere else (normalement pas d'algo dans un controller)
//		var directorOpt = repoPerson.findById(id_person);
//		var movieOpt = repoMovie.findById(id_movie);
//		if (directorOpt.isPresent() && movieOpt.isPresent()) {
//			movieOpt.get().setDirector(directorOpt.get());
//			repoMovie.flush();
//		}
//		return movieOpt;
//	}
//
//	@PutMapping("/modify")
//	@ResponseBody
//	public Optional<Movie> modifyMovie(@RequestBody Movie movie) {
//		var optMovie = repoMovie.findById(movie.getId());
//		//TODO : anywhere else (normalement pas d'algo dans un controller)
//			optMovie.ifPresent(m -> {
//				m.setTitle(movie.getTitle());
//				m.setYear(movie.getYear());
//				m.setDuration(movie.getDuration());
//				m.setDirector(movie.getDirector());
//				repoMovie.flush();	
//			});
//		return optMovie;
//	}
//	
//	@DeleteMapping("/{id}")
//	@ResponseBody
//	public Optional<Movie> deleteMovie(@PathVariable("id") int id){
//		Optional<Movie> movie = repoMovie.findById(id);
//		if (movie.isPresent()) {
//			repoMovie.deleteById(id);
//			repoMovie.flush();
//		}
//		return movie;
//	}
	
}

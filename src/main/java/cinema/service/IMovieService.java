package cinema.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import cinema.persistence.entity.Movie;

public interface IMovieService {
	List<Movie> getAllMovies();
	Optional<Movie> getMovieById(int id);
	Set<Movie> getMovieByPartialTitle(String title);
	Set<Movie> getMovieByTitleAndYear(String title, int year);
	Set<Movie> getMovieByDirector(String director);
	Set<Movie> getMovieByActor(String actor);
	Set<Movie> getMovieByActorOrDirector(String actor, String director);
	
}

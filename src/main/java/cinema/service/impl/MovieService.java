package cinema.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;
import cinema.service.IMovieService;

@Service
@Transactional
public class MovieService implements IMovieService {

	@Autowired
	MovieRepository repoMovie;
	@Autowired
	PersonRepository repoPerson;
	
	@Override
	public List<Movie> getAllMovies() {
		return repoMovie.findAll();
	}

	@Override
	public Optional<Movie> getMovieById(int id) {
		return repoMovie.findById(id);
	}

	@Override
	public Set<Movie> getMovieByPartialTitle(String title) {
		return repoMovie.findByTitleContaining(title);
	}

	@Override
	public Set<Movie> getMovieByTitleAndYear(String title, int year) {
		return repoMovie.findByTitleAndYear(title, year);
	}

	@Override
	public Set<Movie> getMovieByDirector(String director) {
		return repoMovie.findByDirectorName(director);
	}

	@Override
	public Set<Movie> getMovieByActor(String actor) {
		return repoMovie.findByActorsName(actor);
	}

	@Override
	public Set<Movie> getMovieByActorOrDirector(String actor, String director) {
		return repoMovie.findByActorsNameOrDirectorName(actor,director);
	}

}

package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;

@DataJpaTest
class TestMovie {
	
	@Autowired //cree une class auto a partir de l'interface MovieRepository
	MovieRepository repoMovie; 
	
	@Test
	void test() {
		Movie movie = new Movie("Joker", 2019);
		repoMovie.save(movie);
	}

}

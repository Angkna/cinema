package test.cinema.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;

@DataJpaTest
@AutoConfigureTestDatabase
class TestMovie {
	
	@Autowired
	MovieRepository repoMovie;
	
	@Test
	void test() {
		Movie movie = new Movie("Joker", 2019);
		repoMovie.save(movie);
	}

}

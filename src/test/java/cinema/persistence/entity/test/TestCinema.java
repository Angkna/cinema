package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;

@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestCinema {

	@Autowired 
	MovieRepository repoMovie; 
	@Autowired 
	PersonRepository repoPerson;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	void test() {
		Person director = new Person("Gerard", LocalDate.of(1986, 1, 16));
		Movie movie = new Movie("Joker", 2019, director);
		entityManager.persist(director);
		repoMovie.save(movie);
		System.out.println(movie + " avec pour real : " + movie.getDirector());
		assertNotNull(movie.getId());
	}
	
	@Rollback(false)
	@Test
	void testAddDirector() {
		String titre = "Parasite"; //film que l'on veut update
		Person newDirector = new Person("Le ptit nouveau", LocalDate.of(2004, 2, 14));
		repoPerson.save(newDirector); //insert new director
		var movies = repoMovie.findByTitle(titre); //select film
		if (movies.size() > 0 ) {
			var parasite = movies.stream().findFirst().get();
			parasite.setDirector(newDirector); // update film
		}
	}
	
	@Test
	void testSelectMovieWithDirector() {
		String titre = "Parasite";
		var movies = repoMovie.findByTitle(titre); //select film
		if (movies.size() > 0 ) {
			var parasite = movies.stream().findFirst().get(); //double select ici si EAGER 
			var director = parasite.getDirector();// sinon le director charge que la en LAZY
			System.out.println(director);
		}
	}
	
	@Rollback(false)
	@Test
	void testAddMovie() {
		Movie batman = new Movie("Batman Dark Knight", 2008, 153);
		repoMovie.save(batman);
		Person dir = repoPerson.findByNameContaining("patoulatchi").stream().findFirst().get();
		batman.setDirector(dir);
		
	}
	
}

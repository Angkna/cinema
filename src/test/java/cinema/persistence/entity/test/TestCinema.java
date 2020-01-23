package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;

@DataJpaTest 
@AutoConfigureTestDatabase //(replace = Replace.NONE)
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
		entityManager.persist(director);
		entityManager.flush();
		
		Movie movie = new Movie("Joker", 2019, director);
		repoMovie.save(movie);
		repoMovie.flush();
		System.out.println(movie + " avec pour real : " + movie.getDirector());
		assertNotNull(movie.getId());
	}
	
	@Test
	void testAddDirector() {
		Movie movie = new Movie("Parasite", 2019, 181);
		entityManager.persist(movie);//cree le film en bdd
		entityManager.flush();
		
		String titre = "Parasite"; //film que l'on veut update
		Person newDirector = new Person("Le ptit nouveau", LocalDate.of(2004, 2, 14));
		repoPerson.save(newDirector); //insert new director
		repoPerson.flush();
		
		var movies = repoMovie.findByTitle(titre); //select film
		if (movies.size() > 0 ) {
			var parasite = movies.stream().findFirst().get();
			parasite.setDirector(newDirector); // update film
		}
	}
	
	@Test
	void testSelectMovieWithDirector() {
		Person dir = new Person("Le ptit nouveau", LocalDate.of(2004, 2, 14));
		entityManager.persist(dir);
		Movie movie = new Movie("Parasite", 2019, 181, dir );
		entityManager.persist(movie);
		entityManager.flush();
		
		String titre = "Parasite";
		var movies = repoMovie.findByTitle(titre); //select film
		if (movies.size() > 0 ) {
			var parasite = movies.stream().findFirst().get(); //double select ici si EAGER 
			var director = parasite.getDirector();// sinon le director charge que la en LAZY
			System.out.println(director);
		}
	}
	
	@Test
	void testAddMovie() {
		Person director = new Person("Gerard Patoulatchi", LocalDate.of(1986, 1, 16));
		entityManager.persist(director);
		entityManager.flush();
		
		Movie batman = new Movie("Batman Dark Knight", 2008, 153);
		repoMovie.save(batman);
		Person dir = repoPerson.findByNameContaining("Patoulatchi").stream().findFirst().get();
		batman.setDirector(dir);
		repoMovie.flush();
	}
	
	@Test
	void testAddActor() {
		var p2 = new Person("Marcel le 2eme Gros", LocalDate.of(1986, 1, 16));
		var p5 = new Person("Marcel le Vieux", LocalDate.of(1930, 5, 31));
		var p6 = new Person("Marcel Patoulatchi", LocalDate.of(1953, 11, 23));
		var persons = List.of(p2, p5, p6);
		persons.forEach(entityManager::persist);

		var m5 = new Movie("Impitoyable", 1992, 165, p2);
		var m6 = new Movie("Avenger 1", 1992, 175);
		var movies =  List.of(m5, m6);
		
		ArrayList<Person> actors = new ArrayList<Person>();
		actors.add(p6);
		m5.setActors(actors);
		movies.forEach(entityManager::persist);
		entityManager.flush();
		
		var vieux = repoPerson.findByNameContaining("Vieux").stream().findFirst().get();
		var impitoyable = repoMovie.findByTitle("Impitoyable").stream().findFirst().get();
		impitoyable.getActors().add(vieux);
		repoMovie.flush();
	}
	@Test
	void testLazyOrEagerActors() {
		var p1 = new Person("Marcel le Gros", LocalDate.of(1986, 1, 16));
		var p5 = new Person("Marcel le Vieux", LocalDate.of(1930, 5, 31));
		var p6 = new Person("Marcel Patoulatchi", LocalDate.of(1953, 11, 23));
		var persons = List.of(p1, p5, p6);
		persons.forEach(entityManager::persist);
	
		var m1 = new Movie("Joker", 2019, 165, p1);
		var m2 = new Movie("Parasite", 2019, 181);
		var movies =  List.of(m1, m2);
		var actors1 = List.of(p5,p6);
		m1.setActors(actors1);
		movies.forEach(entityManager::persist);
		entityManager.flush();
		
		//read a movie : select movie + director
		var joker = repoMovie.findByTitle("Joker").stream().findFirst().get();
		//read actors : select actor (pas fait avant car LAZY par defaut)
		var actors = joker.getActors();
		System.out.println(actors);
	}
	
	@Test
	void testFindByActorsName() {
		var p1 = new Person("Adrien", LocalDate.of(1986, 1, 16));
		var p2 = new Person("Gérard", LocalDate.of(1986, 1, 16));
		var p3 = new Person("Marcel");
		var persons = List.of(p1, p2, p3);
		persons.forEach(entityManager::persist);
	
		var m1 = new Movie("Joker", 2019, 165, p1);
		var m2 = new Movie("Parasite", 2019, 181);
		var m3 = new Movie("Interstellar", 2014, p1);
		var m4 = new Movie("Gran Torino", 2008, 133, p3);
		m1.getActors().add(p2);
		m2.getActors().add(p3);
		m3.getActors().add(p3);
		m4.getActors().add(p2);
		var movies =  List.of(m1, m2, m3, m4);
		movies.forEach(entityManager::persist);
		entityManager.flush();
		
		var moviesWithMarcelPetit = repoMovie.findByActorsName("Marcel");
		assertAll(
			() -> assertFalse(moviesWithMarcelPetit.contains(m1),"m1"),
			() -> assertTrue(moviesWithMarcelPetit.contains(m2),"m2"),
			() -> assertTrue(moviesWithMarcelPetit.contains(m3),"m3"),
			() -> assertFalse(moviesWithMarcelPetit.contains(m4),"m4"));
	}
	
}

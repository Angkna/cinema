package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;

@DataJpaTest // obligatoire mais va prendre h2 par defaut
@AutoConfigureTestDatabase(replace = Replace.NONE) // pour empecher d'utilisé la base h2 par defaut
class TestMovie {
	
	@Autowired //cree une class auto a partir d'une l'interface (MovieRepository)
	MovieRepository repoMovie; 
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	void test() {
		Movie movie = new Movie("Joker", 2019);
		repoMovie.save(movie);
		System.out.println(movie);
		assertNotNull(movie.getId());
	}
	
	@Test
	void testSelectAll() {
		List<Movie> data = List.of(
				new Movie("Joker", 2019, 165),
				new Movie("Parasite", 2019, 181),
				new Movie("Interstellar", 2014),
				new Movie("Gran Torino", 2008, 133)
				);
		//for (Movie m : data) {}, plus rapide de faire un stream
		data.forEach(entityManager::persist);
		var dataRead = repoMovie.findAll();
		assertEquals(data.size(), dataRead.size());
		assertTrue(dataRead.stream()
				.map(Movie::getTitle)
				.allMatch(t-> data.stream()
						.map(Movie::getTitle)
						.anyMatch(t2 -> t2.equals(t))
				)
		);
				
	}
	
	@Test
	void testSelectById() {
		List<Movie> data = List.of(
				new Movie("Joker", 2019, 165),
				new Movie("Parasite", 2019, 181),
				new Movie("Interstellar", 2014),
				new Movie("Gran Torino", 2008, 133)
				);
		data.forEach(entityManager::persist);
		var id = data.get(2).getId();
		var movieRead = repoMovie.findById(id);
		assertAll(
				() -> assertTrue(movieRead.isPresent()),
				() -> assertEquals(data.get(2).getTitle(), movieRead.get().getTitle())
			);
	}
	
	@Test
	void testFindBy() {
		String title = "Le Roi Lion";
		int year = 2019;
		int year2 = 2000;
		int year3 = 2015;
		List<Movie> data = List.of(
				new Movie("Joker", 2019, 165),
				new Movie("Le Roi Lion", 2019, 181),
				new Movie("Le Roi Lion", 1994),
				new Movie("Gran Torino", 2008, 133)
				);
		data.forEach(entityManager::persist);
		var dataRead = repoMovie.findByTitle(title);
		System.out.println(dataRead);
		
		var dataRead2 = repoMovie.findByYear(year);
		System.out.println(dataRead2);
		
		var dataRead3 = repoMovie.findByYearGreaterThanEqual(year2);
		System.out.println(dataRead3);
		
		var dataRead4 = repoMovie.findByYearBetween(year2, year3);
		System.out.println(dataRead4);
		
		var dataRead5 = repoMovie.findByTitleAndYear(title, year);
		System.out.println(dataRead5);
		
		assertAll(
				()-> assertTrue(dataRead.stream().map(Movie::getTitle).allMatch(t -> t.equals(title))),
				()-> assertTrue(dataRead2.stream().map(Movie::getYear).allMatch(y -> y == year)),
				()-> assertTrue(dataRead3.stream().map(Movie::getYear).allMatch(y -> y >= year2)),
				()-> assertTrue(dataRead4.stream().map(Movie::getYear).allMatch(y -> (y >= year2) && (y <= year3))),
				()-> assertTrue(dataRead5.stream().allMatch(m -> m.getTitle().equals(title) && m.getYear() == year))
		);
		
	}

}

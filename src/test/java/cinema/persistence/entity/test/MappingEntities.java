package cinema.persistence.entity.test;
/**
 *  !!!!!   NOT A UNIT TEST CASE   !!!!!
 */

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;
import cinema.persistence.entity.Movie;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MappingEntities {
	
	@Autowired 
	PersonRepository repoPerson; 
	@Autowired 
	MovieRepository repoMovie; 
	
	@Autowired
	EntityManager entityManager;

	@Rollback(false)
	@Test
	void testSaveData() {
		var p1 = new Person("Marcel le Gros", LocalDate.of(1986, 1, 16));
		var p2 = new Person("Marcel le 2eme Gros", LocalDate.of(1986, 1, 16));
		var p3 = new Person("Marcel le Petit");
		var p4 = new Person("Marcel le Jeune", LocalDate.of(2015, 8, 7));
		var p5 = new Person("Marcel le Vieux", LocalDate.of(1930, 5, 31));
		var p6 = new Person("Marcel Patoulatchi", LocalDate.of(1953, 11, 23));
		var persons = List.of(p1, p2, p3, p4, p5, p6);
		persons.forEach(repoPerson::save);
	
		var m1 = new Movie("Joker", 2019, 165, p1);
		var m2 = new Movie("Parasite", 2019, 181);
		var m3 = new Movie("Interstellar", 2014, p3);
		var m4 = new Movie("Gran Torino", 2008, 133, p2);
		var m5 = new Movie("Impitoyable", 1992, 165, p2);
		var m6 = new Movie("Avenger 1", 1992, 175);
		var m7 = new Movie("Avenger, l'épisode de trop", 2013, 175);
		var m8 = new Movie("Avenger 2", 1996, 145, p3);
		var m9 = new Movie("Le retour des Avenger, oskour !!", 2019, 120, p2);
		var m10 = new Movie("Captain Obvious", 2004, 222, p1);
		var m11 = new Movie("Avenger, encore une bouze", 2008, 350, p2);
		var m12 = new Movie("Il me faut un autre film de 32 !", 1996, 50, p1);
		var movies =  List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12);
		var actors1 = List.of(p5,p6);
		m1.setActors(actors1);
		movies.forEach(repoMovie::save);
		
		
	}

}

package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import cinema.data.Movie;
import cinema.data.Person;

class TestMovie {

	@Test
	void test() {
		Person director = new Person("Marcel",LocalDate.of(1930, 5, 31));
		Movie movie = new Movie("Film de Marcel", 2019, 165, director); 
		//new Movie() -> création d'un objet de type "Movie" grace a un constructeur()
		Movie movie2 = new Movie("Parasite", 2019);
		Movie movie3 = new Movie("Interstellar", 2014); //fonction grace au 2nd constructeur
		movie3.setDuration(32);
		System.out.println(movie);
		System.out.println(movie2);
		System.out.println(movie3);
		System.out.println(movie + " Realisateur : " + movie.getDirector().getName());
		System.out.println(movie + " par " + director 
				+ " agé de " + director.getAge().getAsInt() + " ans.");
		
		List<Movie> movies = List.of(
				movie,
				movie2,
				movie3);
		System.out.println(movies);
	}
	
	@Test
	void testEquals() {
		Movie movieJ = new Movie("joker", 2019);
		Movie movieP = new Movie("Parasite", 2019);
		Movie movie = movieJ;
		System.out.println(movieJ==movieP);
		System.out.println(movieJ==movie);	
	}
	
	@Test
	void testEquals2() {
		Movie movieI = new Movie("joker", 2019);
		Movie movieII = new Movie("joker", 2019);
		System.out.println(movieI==movieII); //false car 2 objet different ont été crée en mémoire (2 new)
		System.out.println(movieI.equals(movieII)); // de base, identique a == . Il faut Override !
		//Movie movieNul = new Movie(null, 2019); // modif du constructeur pour interdire cela
		//System.out.println(movieNul.equals(movieII)); // Interdit de faire une methode sur un null !
	}
}

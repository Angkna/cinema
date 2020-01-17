package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cinema.data.Movie;
import cinema.data.Person;


class TestCinema {
	
	private List<Movie> movies;
	private List<Person> persons;	
	
	@BeforeEach
	void initData() {
		//test ERROR : on ne peut pas add/remove sur un list.of ! (ImmutableCollection)
		// on utilise donc un ArrayList ou LinkedList
		persons = new ArrayList<>(); 
		Collections.addAll(persons,
				new Person("Marcel le Gros", LocalDate.of(1986, 1, 16)),
				new Person("Marcel le 2eme Gros", LocalDate.of(1986, 1, 16)),
				new Person("Marcel le Petit"),
				new Person("Marcel le Jeune", LocalDate.of(2015, 8, 7)),
				new Person("Marcel le Vieux", LocalDate.of(1930, 5, 31)),
				new Person("Marcel Patoulatchi", LocalDate.of(1953, 11, 23))
				);
		var r1 = persons.get(0);
		var r2 = persons.get(1);
		movies = new ArrayList<>();
		Collections.addAll(movies, 
				new Movie("Joker", 2019, 165, r1),
				new Movie("Parasite", 2019, 181),
				new Movie("Interstellar", 2014, r2),
				new Movie("Gran Torino", 2008, 133, r2),
				new Movie("Impitoyable", 1992, 165, r2),
				new Movie("Avenger 1", 1992, 175),
				new Movie("Avenger, l'épisode de trop", 2013, 175),
				new Movie("Avenger 2", 1996, 145, r2),
				new Movie("Le retour des Avenger, oskour !!", 2019, 120, r2),
				new Movie("Captain Obvious", 2004, 222, r1),
				new Movie("Avenger, encore une bouze", 2008, 350, r2),
				new Movie("Il me faut un autre film de 32 !", 1996, 50, r1)
				);
		var r3 = persons.get(2);
		var r4 = persons.get(3);
		var r5 = persons.get(4);
		var r6 = persons.get(5);
		var actorsF1 = List.of(r2,r4,r5);
		movies.get(0).addActor(r3);
		movies.get(1).addAllActors(actorsF1);
		movies.get(2).addActor(r3);
		movies.get(3).addAllActors(r1,r6);
		movies.get(5).addAllActors(r3,r4,r6);
		movies.get(6).addActor(r3);
		movies.get(7).addActor(r4);
		movies.get(8).addAllActors(r3,r5);
		movies.get(10).addAllActors(r2,r5,r6);
		movies.get(11).addActor(r3);
		
	}
	
	@Test
	void test() {
		System.out.println(persons);
		System.out.println(persons.getClass());
		persons.add(new Person("Marcel l'inconnu",LocalDate.of(1975, 11, 27)));
		System.out.println(persons);
	}
	
	@Test
	void testForAvecIndice() {
		for (int i = 0; i < movies.size(); i++ ) {
			var movie = movies.get(i);
			System.out.println(movie + " directed by " + movie.getDirector());
		}
	}
	
	@Test
	void testForEach() {
		for (Movie movie : movies) {
			System.out.println(movie + " directed by " + movie.getDirector());
		} 	
	}
	
	@Test
	void totalDurationOfMoviesDirectedByDirector() {
		Person r = persons.get(3);
		int totalDuration = 0;
		int nonR = 0;
		for (Movie movie : movies) {
			if (movie.getDirector() == r){ // pas de duplication de donnee donc ca marche (sinon faut override un .equals() qui compare les noms
				totalDuration += movie.getDuration();
				if (movie.getDuration() == 0) { 
					nonR++;
					}
			}
		}
		System.out.println("Durée total des films de " + r.getName() + " connue : "
				+ totalDuration + " min. (Il y a " + nonR + " film sans information sur sa durée)");
	}
	@Test
	void totalDurationOfMoviesDirectedByDirectorStreaming() {
		Person r = persons.get(3);
		var dureeTotal = movies.stream()
			.filter(m -> r.equals(m.getDirector()))
			//.forEach(System.out::println); //permet de voir ou on en est dans un pipeline
			.mapToInt(Movie::getDuration)
			.sum();
//			.map(Movie::getDuration)
//			.reduce(0, Integer::sum);
		System.out.println(dureeTotal);
	}
	@Test
	void testAvengerFirstYear() {
		var firstYear = movies.stream()
				.filter(m -> m.getTitle().contains("Avenger"))
				.mapToInt(Movie::getYear)
				.min();
		if  (firstYear.isPresent()) {
		System.out.println("Premiere annee de sortie : " + firstYear.getAsInt());
		} else System.out.println("Pas de résultat");
	}
	
	@Test
	void testAvengerFirstLastYear() {
		var infoYear = movies.stream()
				.filter(m -> m.getTitle().contains("Avenger"))
				.mapToInt(Movie::getYear)
				.summaryStatistics();
			System.out.println("Premiere annee de sortie en " + infoYear.getMin() 
				+ " et derniere anneede sortie en " + infoYear.getMax());
	}
	
	@Test
	void testListeChronologiqueAvengerMovie() {
		var chronoMovie = movies.stream()
				.filter(m -> m.getTitle().contains("Avenger"))
				.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Movie::getYear))));
		System.out.println(chronoMovie);		
	}
	
	@Test
	void testConcetenationOSkour() {
		var phrase = movies.stream()
				.filter(m -> m.getTitle().contains("Avenger"))
				.map(Movie::getTitle)
				.collect(Collectors.joining("; "));
		System.out.println(phrase);
	}
	
	@Test
	void testLimite() {
		movies.stream()
			.filter(m -> m.getYear() > 1958)
			.limit(5)
			.forEach(System.out::println);;
	}
	
	@Test
	void testCount() {
		var film2h = movies.stream()
				.filter(m -> m.getDuration() > 180)
				.count();
		System.out.println(film2h);
	}
	
	@Test
	void testTailleTitrePlusLong() {
		var filmPlusLong = movies.stream()
				.map(Movie::getTitle)
				.mapToInt(String::length)
				.max()
				.getAsInt();
		System.out.println(filmPlusLong);
		//peu efficace car on doit fait le passage une autre fois pour récuperer les titres.
		var listeFilmLong = movies.stream()
		.map(Movie::getTitle)
		.filter(t->t.length()== filmPlusLong)
		.collect(Collectors.toList());
		System.out.println(listeFilmLong);

	}
	
	@Test
	void testFilmByYear() {
		var filmPlusLong = movies.stream()
				.collect(Collectors.groupingBy(Movie::getYear,Collectors.counting()));
		System.out.println(filmPlusLong);
	}
	
	@Test
	void testFilmByDirector() {
		var filmPlusLong = movies.stream()
				.filter(m -> Objects.nonNull(m.getDirector()))
				.collect(Collectors.groupingBy(Movie::getDirector, Collectors.counting()));
		System.out.println(filmPlusLong);
		var filmographie = movies.stream()
				.filter(m -> Objects.nonNull(m.getDirector()))
				.collect(Collectors.groupingBy(
						Movie::getDirector, Collectors.toCollection(
								() -> new TreeSet<>(Comparator.comparing(Movie::getYear,
										Comparator.reverseOrder()))
								)
						)
				);
		System.out.println(filmographie);
	}
	
	@Test
	void testMortCerebralDecade() {
		var poney = persons.stream()
		.filter(d -> Objects.nonNull(d.getBirthdate()))
		.collect(Collectors.groupingBy(p -> p.getBirthdate().getYear()/10));
		System.out.println(poney);
	}
	
	@Test
	void testSortedMovie() {
		//SortedSet<Movie> sortedMovies = new TreeSet<Movie>(movies);
		//marche pas mais ne le montre pas car le compil ne sait pas que on va faire un tri
		SortedSet<Movie> sortedMovies = new TreeSet<Movie>(
				Comparator.comparing(Movie::getYear, Comparator.reverseOrder())
				.thenComparing(Movie::getTitle));
				//(m1,m2) -> -1);
		sortedMovies.addAll(movies);
		System.out.println(sortedMovies);
	}
	
	@Test
	void testSortMovie() {
		//alternative : utilisation de comparateur externe
		Collections.sort(movies, Comparator.comparing(Movie::getYear) 
				//ref de fonction (::), même logique dans les 2 parentheses mais on pref ::
				.thenComparing(m -> m.getTitle())); 
		System.out.println(movies);
	}
	
	@Test
	void testActor() {
		movies.stream()
				.filter(m -> m.getTitle().equals("Avenger 1"))
				.findFirst()
				.ifPresent(System.out::println);
	}
	
	@Test
	void testActorStream() {
		var movie = movies.get(8);
		var a = movie.streamActors()
				.map(p->p.getName())
				.collect(Collectors.joining(", "));
		System.out.println("Acteur(s) du film " + movie.getTitle() + " : " + a + ".");
	}
	
	@Test
	void testActorIterator() {
		var movie = movies.get(5);
		for (var it = movie.interatorActors(); it.hasNext(); ) {
			var actor = it.next();
			System.out.println(actor.getName());
		}
	}
}

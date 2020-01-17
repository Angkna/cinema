package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import cinema.data.Movie;
import cinema.data.Person;

class TestJdbc {
	static DataSource ds;
	
	@BeforeAll
	static void initDataSource() {
		PGSimpleDataSource pgds = new PGSimpleDataSource();
		String host = "localhost"; // "10.31.1.31"
		String username = "cinema";
		String password = "password";
		String dbname = "postgres";
		int port = 5432;
		pgds.setURL("jdbc:postgresql://"
				+ host
				+":"+ port
				+"/"+ dbname);
		pgds.setUser(username);
		pgds.setPassword(password);
		ds = pgds;
	}
	
	@Test
	void testLireFilm() throws SQLException {
		String sql = "select * from film";
		var ListMovies = new ArrayList<Movie>(); // ou bien on peut utilisé une
					// = new TreeSet<Movie>(Comparator.comparing(Movie::getTitle)
					//				.thenComparing(Movie::getYear));
		try (
			Connection conn = ds.getConnection();
			Statement request = conn.createStatement();
			ResultSet res = request.executeQuery(sql);
		){
			while (res.next()) {
				String title = res.getString("titre");
				int year = res.getInt("annee");
				int duration = res.getInt("duree");
				//System.out.println(title + " " + year + " " + duration);
				ListMovies.add(new Movie(title, year, duration));
			}
		} // res/request/conn.close(); inutile car 
		  //Connection/Statement/ResultSet implement l'interface AutoCloseable.
		System.out.println(ListMovies);
		int dureeTotal = 0;
		for (Movie m : ListMovies) {
			if (m.getDuration()!=0) {
				System.out.println(m.getTitle() + " de durée " + m.getDuration() + "min");
				dureeTotal += m.getDuration();
			}else System.out.println(m.getTitle() + " de durée inconnu");
		}
		System.out.println("Durée total : " + dureeTotal + "min");
	}
	
	@Test
	void testLireFilmsFiltre() throws SQLException {
		int thresholdDuration = 120;
		String sql = "select * from film where duree >= ?";
		var ListMovies = new TreeSet<Movie>(Comparator.comparing(Movie::getTitle)
						.thenComparing(Movie::getYear));
		try (
			Connection conn = ds.getConnection();
			PreparedStatement request = conn.prepareStatement(sql);
			// ResultSet res = request.executeQuery(sql + thresholdDuration); 
			// Interdit de concaténer, trop dangereux sur la sécurité !
		){
			request.setInt(1, thresholdDuration); //remplace le 1er  ? par la valeur donnée
			try (ResultSet res = request.executeQuery()){ // try séparer car une instruction intermediaire
				while (res.next()) {
					String title = res.getString("titre");
					int year = res.getInt("annee");
					int duration = res.getInt("duree");
					ListMovies.add(new Movie(title, year, duration));
				}
			}
		}
		System.out.println(ListMovies);
		assertAll(ListMovies.stream()
				.map(m -> () -> assertTrue(m.getDuration() >= thresholdDuration, m.getTitle())));
	}
	
	@Test
	void testAddPerson() throws SQLException {
		var persons = List.of(
				new Person("Marcel lePetit", LocalDate.of(1975, 12, 31)),
				new Person("Marcel leJeune", LocalDate.of(2015, 8, 7)),
				new Person("Marcel leVieux", LocalDate.of(1930, 5, 31))
				);
		String sql = "insert into individu (prenom, nom, date_naissance) values (?,?,?)";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement request = conn.prepareStatement(sql);
			){
				for (Person p : persons) {
					var tab = p.getName().split(" ");
					request.setString(1, tab[0]);
					request.setString(2, tab[1]);
					request.setDate(3, Date.valueOf(p.getBirthdate()));
					//request.executeUpdate();
				}
			}
	}
}

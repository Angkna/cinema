package cinema.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ //object, primary key
	//requete qui seront auto generer, pas besoin de code.
	Set<Movie> findByTitle(String title);
	Set<Movie> findByTitleContaining(String title);
	Set<Movie> findByYear(int year);
	Set<Movie> findByYearGreaterThanEqual(int year);
	Set<Movie> findByYearBetween(int yearStart,int yearEnd);
	Set<Movie> findByTitleAndYear(String title,int year);
	Set<Movie> findByDirector(Person director);
	Set<Movie> findByDirectorName(String name);
	Set<Movie> findByDirectorNameContaining(String name);
	// (findBy : 'select * from') <Movie> (where)  
	// (TitleAndYear(String title,int year) : 'Title = title and Year = year')
}

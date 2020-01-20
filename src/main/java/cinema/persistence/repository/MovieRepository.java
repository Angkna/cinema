package cinema.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.persistence.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ //object, primary key
	Set<Movie> findByTitle(String title);
	Set<Movie> findByYear(int year);
	Set<Movie> findByYearGreaterThanEqual(int year);
	Set<Movie> findByYearBetween(int yearStart,int yearEnd);
	Set<Movie> findByTitleAndYear(String title,int year);
	// (findBy : 'select * from') <Movie> (where)  
	// (TitleAndYear(String title,int year) : 'Title = title and Year = year')
}

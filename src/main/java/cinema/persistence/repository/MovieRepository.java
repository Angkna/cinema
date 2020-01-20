package cinema.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.persistence.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ //object, primary key

}

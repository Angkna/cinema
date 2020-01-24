package cinema.service;

import java.util.List;
import java.util.Optional;

import cinema.persistence.entity.Person;

public interface IPersonService {
	List<Person> getAllPersons();
	Optional<Person> getPersonById(int id);
	Person addPerson(Person person);
}

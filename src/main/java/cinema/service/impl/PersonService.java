package cinema.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;
import cinema.service.IPersonService;

@Service
@Transactional
public class PersonService implements IPersonService {
	
	@Autowired
	MovieRepository repoMovie;
	PersonRepository repoPerson;
	
	@Override
	public List<Person> getAllPersons() {
		return repoPerson.findAll();
	}
	@Override
	public Optional<Person> getPersonById(int id) {
		return repoPerson.findById(id);
	}
	@Override
	public Person addPerson(Person person) {
		var saved = repoPerson.save(person);
		repoPerson.flush();
		return saved;
	}
	
}

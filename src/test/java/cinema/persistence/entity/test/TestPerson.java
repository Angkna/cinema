package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cinema.persistence.entity.Person;
import cinema.persistence.repository.PersonRepository;



@DataJpaTest
class TestPerson {

	@Autowired 
	PersonRepository repoPerson; 
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	void test() {
		Person person = new Person("Marcel" ,LocalDate.of(1955, 07, 29));
		repoPerson.save(person);
		assertNotNull(person.getId());
		System.out.println(person + " avec l'ID " + person.getId());
	}
	
	@Test
	void testRepo() {
		List<Person> data = List.of(
		new Person("Marcel le Gros", LocalDate.of(1986, 1, 16)),
		new Person("Gerard", LocalDate.of(1986, 1, 16)),
		new Person("Marion Marechal"),
		new Person("Jean Lucien de la Voute", LocalDate.of(2015, 8, 7)),
		new Person("Marion", LocalDate.of(1930, 5, 31)),
		new Person("Marcel Patoulatchi Agent de la Paix", LocalDate.of(1953, 11, 23)));
		data.forEach(entityManager::persist);
		
		var dataRead = repoPerson.findByName("Marion");
		var dataMarion = repoPerson.findByNameContaining("Marion");
		System.out.println(dataRead);
		System.out.println(dataMarion);
		
	}
	
	@Test
	void testByYear() {
		List<Person> data = List.of(
		new Person("Marcel le Gros", LocalDate.of(1986, 1, 16)),
		new Person("Gerard", LocalDate.of(1930, 1, 16)),
		new Person("Marion Marechal"),
		new Person("Jean Lucien de la Voute", LocalDate.of(2015, 8, 7)),
		new Person("Marion", LocalDate.of(1930, 5, 31)),
		new Person("Marcel Patoulatchi Agent de la Paix", LocalDate.of(1953, 11, 23)));
		data.forEach(entityManager::persist);
		
		int year = 1930;
		var data2 = repoPerson.findByBirthdateYear(year);
		System.out.println(data2);
	}
}

package cinema.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cinema.persistence.entity.Person;
import cinema.persistence.repository.PersonRepository;
import cinema.service.IPersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {
	
	@Autowired
	IPersonService personService;
	PersonRepository repoPerson;
	
	@GetMapping
	@ResponseBody
	public List<Person> allPersons(){
		return personService.getAllPersons();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<Person> personById(@PathVariable("id") int id){
		return personService.getPersonById(id);
	}
	
	@PostMapping
	@ResponseBody
	public Person addPerson (@RequestBody Person p) {
		return personService.addPerson(p);
	}
}

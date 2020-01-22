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

@RestController
@RequestMapping("/api/person")
public class PersonController {
	
	@Autowired
	PersonRepository repoPerson;
	
	@GetMapping
	@ResponseBody
	public List<Person> persons(){
		return repoPerson.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<Person> person(@PathVariable("id") int id){
		return repoPerson.findById(id);
	}
	
	@PostMapping
	@ResponseBody
	public Person addPerson (@RequestBody Person p) {
		var saved = repoPerson.save(p);
		repoPerson.flush();
		return saved;
	}
}

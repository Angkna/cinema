package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import cinema.data.Person;

class TestPerson {

	@Test
	void testCreate() {
		Person p = new Person("Marcel le Gros");
		System.out.println(p);
		p.setBirthdate(LocalDate.of(1984, 02, 02));
		System.out.println(p + " Age : " + p.getAge());
	}

}

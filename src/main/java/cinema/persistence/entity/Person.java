package cinema.persistence.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Person {
	private Integer id;
	private String name;
	private LocalDate birthdate;
	
	public Person() {
		super();
	}
	public Person(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}
	public Person(String name, LocalDate birthdate) {
		this(null, name, birthdate);
	}
	public Person(String name) {
		this(null, name, null);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(name);
		if (Objects.nonNull(birthdate)) {
			builder.append(" (").append(birthdate).append(")");
		}
		return builder.toString();
	}
	
	
	
	
}

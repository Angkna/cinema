package cinema.data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.OptionalInt;

public class Person {
	private String name;
	private LocalDate birthdate;
	
	public Person(String name, LocalDate birthdate) {
		super();
		this.name = Objects.requireNonNull(name);
		this.birthdate = birthdate;
	}
	public Person(String name) {
		this(name,null);
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	@Override
	public String toString() {
		return name + " ("+ Objects.toString(birthdate, "-") + ")";
	}
	
	public OptionalInt getAge() {
		if (Objects.isNull(birthdate)) {
			return OptionalInt.empty();
		}
		LocalDate today = LocalDate.now();
		int year = today.getYear() - birthdate.getYear();
		if (today.getMonthValue() < birthdate.getMonthValue()) {
			return OptionalInt.of(year - 1);			
		} 
		if (today.getMonthValue() == birthdate.getMonthValue()) {
			if (today.getDayOfMonth() < birthdate.getDayOfMonth()) {
				return OptionalInt.of(year - 1);
			}
		}
		return OptionalInt.of(year);
		
	}
}

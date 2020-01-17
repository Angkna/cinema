package cinema.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Movie {
	//attributes
	private String title;
	private int year;
	private int duration;
	private Person director;
	private List<Person> actors;
	
	//Getter and Setter
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public void addActor(Person actor) {
		this.actors.add(actor);	
	}
	public void addAllActors(Collection<? extends Person> actors) {
		this.actors.addAll(actors);	
	}
	public void addAllActors(Person...persons) {
//		for (Person p : persons) {
//			this.actors.add(p);
//		}
		this.addAllActors(Arrays.asList(persons));
	}
	public Iterator<Person> interatorActors(){
		return this.actors.iterator();
	}
	public Stream<Person> streamActors(){
		return this.actors.stream();
	}
	
	//les constructeurs
	public Movie(String title, int year, int duration, Person director) {
		//super(); //n'est pas obligatoire car tout constructeur appel celui de la classe parent par defaut
		this.title = Objects.requireNonNull(title); //modif pour empecher le null.
		this.year = year;
		this.duration = duration;
		this.director = director;
		this.actors = new ArrayList<Person>();
	}
	public Movie(String title, int year, int duration) {
		this(title, year, duration, null); 
	}
	public Movie(String title, int year) {
		this(title, year, 0, null); // remplace le super() : il appel celui au dessus comme base
	}
	public Movie(String title, int year, Person director) {
		this(title, year, 0, director); 
		
	}
	
//	public Movie(String title, int duration, Person director) {
//		this(title, 0, duration, director); 
//	}
//   IMPOSSIBLE : On ne peut pas créer 2 constructeur avec exactement les même types en entrer !
	
	@Override
	public String toString() {
		return title + " (" + year
				+ (duration==0 ? "" : ", " + duration + " min") // condition
				 + ")";
	}
	//le hashcode et  le equals doivent utilisé les mêmes parametres sinon ca m'a aucun sens
	@Override
	public int hashCode() {
		//return Objects.hash(title, year);
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// cas particulier, extreme et détermination de meme type.
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) // Movie.instanceOf(Object)->true si on veut utilisé héritage
			return false;
		// on est sur que l'objet est un movie donc on force la transformation
		Movie other = (Movie) obj;
		// on peut faire les test d'égalité que l'on veut sur 2 movies
		// on vérifie les cas null
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	
	
}

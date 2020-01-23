package cinema.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
	//@Column sur les attributs OU BIEN sur les getters(mieux les getters)
	private Integer id;
	private String title;
	private Integer year;
	private Integer duration; 
	// on évite les types primitifs pour pouvoir avec null.
	private Person director;
	private List<Person> actors; //attention au NullPointerException
	
	public Movie() {
		this(null, null);
	}
	public Movie(Integer id, String title, Integer year, Integer duration, Person director) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.duration = duration;
		this.director = director;
		this.actors = new ArrayList<>(); //on met une liste vide !
	}
	public Movie(String title, Integer year, Integer duration, Person director) {
		this(null, title, year, duration, director);
	}
	public Movie(String title, Integer year, Integer duration) {
		this(null, title, year, duration, null);
	}
	public Movie(String title, Integer year, Person director) {
		this(null, title, year, null, director);
	}
	public Movie(String title, Integer year) {
		this(null, title, year, null, null);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movie")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "title", nullable = false, length = 255)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(nullable = false)
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	//lien de table avec Person : cle etrangere (FK)
	@ManyToOne(fetch = FetchType.EAGER) //Eager par defaut 1-n (doit use LAZY pour des relations circulaires).
	@JoinColumn(name = "id_director", nullable = true)
	public Person getDirector() {
		return director;
	}
	
	public void setDirector(Person director) {
		this.director = director;
	}
	
	//Normalement on ne les fait pas pour n-n mais comme c'est pour la persistance, on se le permet.
	@ManyToMany(fetch = FetchType.LAZY) //Lazy par defaut (relation n-n)
    @JoinTable(name="act",
    joinColumns = @JoinColumn(name="id_movies", referencedColumnName="id_movie"),
    inverseJoinColumns = @JoinColumn(name="id_actor", referencedColumnName="id_person"))
	public List<Person> getActors() {
		return actors;
	}
	
	public void setActors(List<Person> actors) {
		this.actors = actors;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(title).append(" (").append(year).append(")#").append(id);
		return builder.toString();
	}
	
	
}

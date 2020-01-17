package cinema.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Transient;

@Entity
@Table(name = "movies")
public class Movie {
	//@Column sur les attributs OU BIEN sur les getters(mieux les getters)
	private Integer id;
	private String title;
	private Integer year;
	private Integer duration; 
	// on évite les types primitifs pour pouvoir avec null.
//	@Transient
//	private String director;
	
	public Movie() {
		super();
	}
	public Movie(Integer id, String title, Integer year, Integer duration) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.duration = duration;
	}
	public Movie(String title, Integer year, Integer duration) {
		this(null, title, year, duration);
	}
	public Movie(String title, Integer year) {
		this(null, title, year, null);
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(title).append("  (").append(year).append(")");
		return builder.toString();
	}
	
	
}

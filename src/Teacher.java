import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="teachers")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String pesel;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
	name="schoolClasses_teachers",
	joinColumns=@JoinColumn(name="teacher_id"),
	inverseJoinColumns=@JoinColumn(name="schoolClass_id")
	)
	Set<SchoolClass> classes = new HashSet<SchoolClass>();
	
	public Teacher() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	
	public Set<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(Set<SchoolClass> classes) {
		this.classes = classes;
	}
	
	public void addClass(SchoolClass schoolClass)
	{
		classes.add(schoolClass);
	}
	
	public void removeClass(SchoolClass schoolClass)
	{
		classes.remove(schoolClass);
	}

	public String toString() {
		return "Teacher: " + getName() + " " + getSurname() + " (" + getPesel() + ")";
	}
}

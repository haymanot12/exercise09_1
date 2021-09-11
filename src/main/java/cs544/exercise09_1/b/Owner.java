package cs544.exercise09_1.b;

import javax.persistence.*;
import java.util.List;

@Entity
public class Owner {
	@Id  
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany (cascade={CascadeType.PERSIST})
	@org.hibernate.annotations.BatchSize(size = 30)
    @JoinColumn (name="clientid")
    private List<Pet> pets;
    
	public Owner() {
	}
	public Owner(String name) {
		super();
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
    
	
    
}

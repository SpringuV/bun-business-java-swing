package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "foods")
public class Food {
	@Id
	@GeneratedValue(strategy =  GenerationType.UUID)
	@Column(name = "id_food", unique = true)
	private String id_food;
	
	@Column(name = "name_food")
	private String name_food;
	
	@Column(name= "prices")
	private double prices;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image_path")
	private String image_path;
	
	@Column(name="type_food")
	private String type_food;
	
	@Column(name="state_food")
	private String state_food; // dang bán hoặc ngừng bán

	public Food() {
		super();
	}

	public String getId_food() {
		return id_food;
	}

	public void setId_food(String id_food) {
		this.id_food = id_food;
	}

	public String getName_food() {
		return name_food;
	}

	public void setName_food(String name_food) {
		this.name_food = name_food;
	}

	public double getPrices() {
		return prices;
	}

	public void setPrices(double prices) {
		this.prices = prices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getType_food() {
		return type_food;
	}

	public void setType_food(String type_food) {
		this.type_food = type_food;
	}

	public String getState_food() {
		return state_food;
	}

	public void setState_food(String state_food) {
		this.state_food = state_food;
	}
}

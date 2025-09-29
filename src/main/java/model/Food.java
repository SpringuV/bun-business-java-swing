package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Column(name = "is_available")
	private boolean is_available = true; // daily availability
	
	@OneToMany(mappedBy = "food", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<OrderItem> order_item_list;

}

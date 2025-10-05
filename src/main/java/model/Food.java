package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Column(name = "id_food", unique = true)
	String id_food;
	
	@Column(name= "name_food")
	String name_food;
	
	@Column(name= "prices")
	double prices;
	
	@Column(name="description")
	String description;
	
	@Column(name="image_path")
	String image_path;
	
	public enum FoodType {
	    APPETIZER,      // món khai vị
	    MAIN_COURSE,    // món chính
	    DESSERT,        // tráng miệng
	    DRINK,          // đồ uống
	    SPICY,          // cay
	    SWEET,          // ngọt
	    VEGETARIAN,     // chay
	    SEAFOOD,        // hải sản
	    FAST_FOOD       // đồ ăn nhanh
	}
	@Enumerated(EnumType.STRING)
	@Column(name="type_food")
	FoodType type_food;
	
	@Builder.Default
	@Column(name = "is_available")
	boolean is_available = true; // daily availability
	
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
	List<OrderItem> order_item_list;

}

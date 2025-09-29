package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id_user;
	
	@Column(name = "phone_number", unique = true)
	String phone_number;
	
	@Column(name = "pass", unique = true)
	String pass;
	
	@Column(name = "role")
	String role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Order> order_list;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<InventoryTransaction> inventory_transaction_list;
}

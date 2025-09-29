package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name="customers")
public class Customer {
	
	@Id
	@Column(name="phone_number")
	@Size(min = 10, max = 10, message = "Bạn phải nhập đúng 10 số của số điện thoại !")
	@Pattern(regexp = "^[0-9]+$", message = "Không nhập kí tự chữ")
	private String phone_number;
	
	@Column(name="name_cus")
	private String name_cus;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Invoice> invoice_list;
	
	@Column(name="sex_cus")
	private boolean sex_cus;
	
	@Column(name="age_cus")
	private int age_cus;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Order> order_list;
}

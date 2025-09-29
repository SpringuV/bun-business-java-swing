package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@Column(name="phone_number")
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
}

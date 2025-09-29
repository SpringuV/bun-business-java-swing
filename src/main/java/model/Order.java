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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private int id_order;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Invoice invoice;

	@ManyToMany
	@JoinTable(name = "reservation", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "guest_table_id"))
	private List<GuestTable> guest_table_list;
}

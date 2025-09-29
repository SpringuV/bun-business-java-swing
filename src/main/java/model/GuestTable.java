package model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tables")
public class GuestTable {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_table", unique = true)
	private String id_table; //Mã bàn
	
	@Column(name = "name_table", unique = true)
    private String name_table; //Tên bàn
	
	@Column(name = "type_table", unique = true)
    private String type_table; //Loại bàn
	
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order orders;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime created_at;
	
	@Column(name = "updated_at")
    private LocalDateTime updated_at;
	
	public GuestTable() {
		super();
	}

	public Order getOrders() {
		return orders;
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}

	public String getId_table() {
		return id_table;
	}

	public void setId_table(String id_table) {
		this.id_table = id_table;
	}

	public String getName_table() {
		return name_table;
	}

	public void setName_table(String name_table) {
		this.name_table = name_table;
	}

	public String getType_table() {
		return type_table;
	}

	public void setType_table(String type_table) {
		this.type_table = type_table;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	
}

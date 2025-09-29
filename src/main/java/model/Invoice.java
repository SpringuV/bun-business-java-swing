package model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {

	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_invoice")
	private String id_invoice;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user; 
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime created_at;
	
	@Column(name = "updated_at")
    private LocalDateTime updated_at;
	
	@OneToOne
	@JoinColumn(name = "id_order")
	private Order orders;
	
	@Column(name = "discount")
	private double discount;
	
	public Invoice() {
		super();
	}

	public Order getOrders() {
		return orders;
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}

	public String getId_invoice() {
		return id_invoice;
	}

	public void setId_invoice(String id_invoice) {
		this.id_invoice = id_invoice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}

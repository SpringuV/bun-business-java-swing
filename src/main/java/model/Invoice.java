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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	
	public enum PaymentMethod {CASH, CARD, BANKING}
	@Column(name = "payment_method")
	private String payment_method; // "CASH", "CARD", "DIGITAL_WALLET"

	public enum PaymentStatus {PENDING, PAID, REFUNDED}
	@Column(name = "payment_status")
	private String payment_status; // "PENDING", "PAID", "REFUNDED"
	
	@PrePersist
	protected void onCreate() {
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_at = LocalDateTime.now();
	}
}

package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_invoice")
	String id_invoice;

	@ManyToOne
	@JoinColumn(name = "id_user")
	User user;

	@Column(name = "created_at", updatable = false)
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;

	@OneToOne
	@JoinColumn(name = "id_order")
	Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	Customer customer;

	@Column(name = "discount")
	double discount;

	public enum PaymentMethod {
		CASH, CARD, BANKING, DIGITAL_WALLET
	}
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	PaymentMethod payment_method;

	public enum PaymentStatus {
		PENDING, PAID, REFUNDED
	}
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	PaymentStatus payment_status; 
	
	@Column(name = "note")
	String note;
	
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

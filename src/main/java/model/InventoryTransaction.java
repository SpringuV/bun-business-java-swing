package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import model.Invoice.PaymentMethod;
import model.Invoice.PaymentStatus;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_transactions")
public class InventoryTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_trans")
	Long id;

	@Column(name="quantity")
	int quantity; // số lượng nhập/xuất
	
	public enum TypeInventoryTransaction {IN, OUT}
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	TypeInventoryTransaction type; // "IN" hoặc "OUT"
	
	@Column(name="transaction_time")
	LocalDateTime transaction_time;
	
	@Column(name="note")
	String note; // tùy chọn
	
	@ManyToOne
	@JoinColumn(name = "perform_by_user_id")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "id_ingredient")
	Ingredient ingredient;
	
	@Column(name = "created_at", updatable = false)
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;
	
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

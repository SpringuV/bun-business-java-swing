package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_transactions")
public class InventoryTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_trans")
	private Long id;

	@Column(name="quantity")
	private int quantity; // số lượng nhập/xuất
	
	@Column(name="type")
	private String type; // "IN" hoặc "OUT"
	
	@Column(name="transaction_time")
	private LocalDateTime transaction_time;
	
	@Column(name="note")
	private String note; // tùy chọn
	
	@ManyToOne
	@JoinColumn(name = "perform_by_user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_ingredient")
	private Ingredient ingredient;
}






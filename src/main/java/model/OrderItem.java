package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "order_item")
public class OrderItem {

	@EmbeddedId
	KeyOrderItem keyOrderItem;

	@ManyToOne
	@MapsId("id_order")
	@JoinColumn(name = "id_order")
	Orders order;

	@ManyToOne
	@MapsId("id_food")
	@JoinColumn(name = "id_food")
	Food food;

	@Column(name = "quantity")
	int quantity;

	@Column(name = "price")
	double price;
	
	@Column(name = "special_requests")
	String special_requests; // customer notes

	@Column(name = "unit")
	String unit;

	@Column(name = "size")
	String size;

	@Column(name = "discount")
	double discount;
	
	@Column(name="note")
	String note;

	@Column(name = "created_at", updatable = false)
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;

	@PrePersist
	protected void onCreate() {
		this.created_at = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_at = LocalDateTime.now();
	}

}

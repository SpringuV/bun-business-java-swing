package model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "ingredients")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_ingredient", unique = true)
	String id_ingredient;
	
	@Column(name = "name_ingredient", nullable = false, unique = true)
	String name_ingredients; // tên nguyên liệu

	@Column(name = "prices")
	double prices;

	@Column(name = "quantity")
	int quantity; // hàng tồn kho hiện tại

	public enum UnitOfMeasurement {
		KG, GRAM, CÁI, BÓ, THÙNG, HỘP, TÚI, CHIẾC, VIÊN, ĐÔI, LỌ, BÌNH
	}
	@Column(name = "unit_of_measurement")
	@Enumerated(EnumType.STRING)
	UnitOfMeasurement unit_of_measurement; // đơn vị đo: cái, kg

	@Column(name = "description")
	String description;

	@Column(name = "supplier")
	String supplier;

	@Column(name = "created_at", updatable = false) // chỉ set 1 lần khi insert.
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;

	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	List<InventoryTransaction> inventory_transaction_list;

	// method
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

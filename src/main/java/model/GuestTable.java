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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "guest_tables")
public class GuestTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_table", unique = true)
	int id_table; // Mã bàn

	@ManyToMany
	@JoinTable(name = "reservation", joinColumns = @JoinColumn(name = "id_table"), inverseJoinColumns = @JoinColumn(name = "id_order"))
	List<Orders> order_list;

	@Column(name = "created_at", updatable = false) // chỉ set 1 lần khi insert.
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;

	public enum TypeTable {VIP, NORMAL}
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	TypeTable type;

	@Builder.Default
	@Column(name = "is_available")
	boolean is_available = true;

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

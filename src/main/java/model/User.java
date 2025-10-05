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
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", indexes = {
		@Index(name = "idx_user_phone", columnList = "phone_number")
})
@ToString(exclude = { "order_list", "inventory_transaction_list" }) // Tránh vòng lặp toString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id_user;

	@Column(name = "phone_number", unique = true)
	String phone_number;

	@Column(name = "full_name")
	String full_name;

	@Column(name = "pass")
	String pass;

	@Column(name = "role")
	String role;

	public enum UserStatus {
	    ACTIVE,      // đang làm
	    INACTIVE,    // tạm nghỉ, không hoạt động
	    RESIGNED     // đã nghỉ việc
	}
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	UserStatus status;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Orders> order_list;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<InventoryTransaction> inventory_transaction_list;
	
	@Column(name = "created_at", updatable = false)
    LocalDateTime created_at;
	
	// tự động set created_at khi mới tạo
    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now();
    }
}

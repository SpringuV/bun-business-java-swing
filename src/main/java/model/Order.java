package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	int id_order;

	@ManyToOne
	@JoinColumn(name = "id_user")
	User user;

	@OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
	Invoice invoice; 

//	Tránh NullPointerException khi dùng builder hoặc thêm phần tử vào list.
//	Khi dùng builder của Lombok, nếu không thêm @Builder.Default, các list sẽ là null.
	@Builder.Default
	@ManyToMany(mappedBy = "order_list", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	List<GuestTable> guest_table_list = new ArrayList<>();
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	List<OrderItem> order_item_list = new ArrayList<>();	
	
//	Tăng type safety, tránh nhập sai giá trị trạng thái.
//	Lưu vào DB dạng String, dễ đọc/tra cứu.
	public enum OrderStatus { PENDING, PREPARING, READY, SERVED, CANCELLED }
	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	String order_status; // "PENDING", "PREPARING", "READY", "SERVED", "CANCELLED"

	public enum OrderType { DELIVERY, TAKEAWAY, DINE_IN }
	@Column(name = "order_type")
	@Enumerated(EnumType.STRING)
	String order_type; // "DINE_IN", "TAKEAWAY", "DELIVERY"

	@Column(name = "total_amount")
	double total_amount;

	@Column(name = "special_requests")
	String special_requests; // customer notes

	@Column(name = "estimated_ready_time")
	LocalDateTime estimated_ready_time;
	
	@Column(name = "created_at", updatable = false) // chỉ set 1 lần khi insert.
	LocalDateTime created_at;

	@Column(name = "updated_at")
	LocalDateTime updated_at;

	@ManyToOne
	@JoinColumn(name = "id_customer")
	Customer customer;
	
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

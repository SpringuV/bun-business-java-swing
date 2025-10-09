package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ware_houses")
public class WareHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_warehouse")
	String id_warehouse;

	@Column(name = "code_whs")
	String code_warehouse;

	@Column(name = "name_whs")
	String name_warehouse;

	@Column(name = "address_whs")
	String address_warehouse;
}
